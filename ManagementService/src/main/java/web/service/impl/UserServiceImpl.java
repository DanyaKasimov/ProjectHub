package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.accessor.EmailService;
import web.dto.request.SignUpDto;
import web.dto.request.UserEditDto;
import web.dto.response.*;
import web.dto.response.email.EmailContentDto;
import web.dto.response.email.EmailCreateDto;
import web.dto.response.email.EmailDto;
import web.dto.response.email.EmailSendDto;
import web.exception.InvalidDataException;
import web.exception.NoDataFoundException;
import web.mappers.UserMapper;
import web.model.Company;
import web.model.User;
import web.repositories.UserRepository;
import web.service.CompanyService;
import web.service.DeletionRequestService;
import web.service.UserService;
import web.utils.FileHandler;
import web.utils.Generator;
import web.utils.JsonUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CompanyService companyService;

    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, EmailSendDto> kafkaTemplate;

    private final UserMapper userMapper;

    private final EmailService emailService;

    private final DeletionRequestService deletionRequestService;

    @Value("${spring.kafka.topic.send-email}")
    private String topic;

    @Override
    @Transactional
    public UserDto addEmployee(final SignUpDto signUpDto) {
        log.info("Добавление нового сотрудника для компании ID: {}", signUpDto.getCompanyId());

        Company company = companyService.findById(signUpDto.getCompanyId());
        val domain = company.getDomain();

        val username =
                Generator.generateUsername(signUpDto.getName(), signUpDto.getSurname(), signUpDto.getPatronymic(), domain);

        if (userRepository.existsByUsernameAndCompany(username, company)) {
            throw new InvalidDataException(
                    String.format("Имя пользователя %s занято в компании %s.", username, company.getName()));
        }

        val password = Generator.generatePassword();

        EmailCreateDto emailCreateDto = EmailCreateDto.builder()
                .name(signUpDto.getName())
                .surname(signUpDto.getSurname())
                .patronymic(signUpDto.getPatronymic())
                .domain(domain)
                .build();

        EmailDto emailResponse = JsonUtil.fromString(
                JsonUtil.toJsonString(emailService.createEmail(emailCreateDto).getResult()),
                EmailDto.class
        );

        val user = userRepository.save(
                User.builder()
                        .name(signUpDto.getName())
                        .surname(signUpDto.getSurname())
                        .patronymic(signUpDto.getPatronymic())
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .position(signUpDto.getPosition())
                        .emailId(emailResponse.getId())
                        .emailRoot(signUpDto.getEmail())
                        .company(company)
                        .role(signUpDto.getRole())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        sendEmail(signUpDto.getEmail(), emailResponse.getName(), username, password, "email-start");

        return userMapper.toDto(user);
    }

    private void sendEmail(String emailRoot, String emailCompany, String username, String password, String template) {
        val emailContent = EmailContentDto.builder()
                .email(emailCompany)
                .password(password)
                .username(username)
                .build();

        val emailMessage = EmailSendDto.builder()
                .address(emailRoot)
                .content(generateEmailBody(emailContent, template))
                .build();

        kafkaTemplate.send(topic, emailMessage);
    }

    private String generateEmailBody(EmailContentDto emailDto, String template) {
        String body = FileHandler.loadFromTemplate(template);
        body = body.replace("{{ email }}", emailDto.getEmail());
        body = body.replace("{{ username }}", emailDto.getUsername());
        body = body.replace("{{ password }}", emailDto.getPassword());
        return body;
    }

    @Override
    @Transactional
    @CacheEvict(value = {"users.data.username", "users.data.id"}, key = "#dto.id")
    public void updateEmployee(final UserEditDto dto) {
        log.info("Обновление данных сотрудника.");

        val user = userRepository.findById(dto.getId()).orElseThrow(
                () -> new NoDataFoundException("Сотрудник не найден.")
        );

        if (companyService.findById(dto.getCompanyId()) == null) {
            throw new NoDataFoundException(String.format("Компания с ID = %s не найдена", dto.getCompanyId()));
        }

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPatronymic(dto.getPatronymic());
        user.setPosition(dto.getPosition());
        user.setRole(dto.getRole());
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }


    @Override
    @Transactional
    @CacheEvict(value = {"users.data.username", "users.data.id"}, key = "#id")
    public void updatePasswordEmployee(final UUID id) {
        log.info("Обновление пароля сотрудника.");

        val user = userRepository.findById(id).orElseThrow(
                () -> new NoDataFoundException("Сотрудник не найден.")
        );

        val password = Generator.generatePassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.saveAndFlush(user);

        String email = emailService.getEmail(user.getEmailId()).getResult().toString();
        sendEmail(user.getEmailRoot(), email, user.getUsername(), password, "email-update");
    }


    @Override
    @Transactional
    public void deleteEmployees(final List<UUID> ids) {
        log.info("Удаление сотрудников начато.");

        ids.forEach(this::deleteEmployee);

        log.info("Сотрудники удалены.");
    }


    // TODO Возможно сломается из за кэша
    @Transactional
    @CacheEvict(value = {"users.data.username", "users.data.id"}, key = "#id")
    protected void deleteEmployee(final UUID id) {
        userRepository.delete(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new NoDataFoundException(
                                String.format("Сотрудник c ID = %s не найден.", id)
                        )));
    }


    @Override
    @Cacheable(value = "users.data.username", key = "#username")
    public UserDataDto findByUsername(final String username) {
        val user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoDataFoundException("Пользователь не найден.")
        );
        return userMapper.toDtoLite(user);
    }


    @Override
    @Cacheable(value = "users.data.id", key = "#id")
    public UserDataDto findById(final UUID id) {
        val user = userRepository.findById(id).orElseThrow(
                () -> new NoDataFoundException("Пользователь не найден.")
        );
        return userMapper.toDtoLite(user);
    }

    @Override
    @Cacheable(value = "company.users.list", key = "#id + '_' + #pageable.pageNumber")
    public Page<UserDataDto> findAllByCompany(final UUID id, final Pageable pageable) {
        Company company = companyService.findById(id);

        Page<User> users = userRepository.findAllByCompany(company, pageable);

        List<UserDataDto> usersDto = users.stream()
                .map(userMapper::toDtoLite)
                .toList();

        return new PageImpl<>(usersDto, pageable, users.getTotalElements());
    }


    @Override
    @Cacheable(value = "company.users.id.list", key = "#companyID")
    public List<UUID> findAllIdsByCompany(final UUID companyID) {
        Company company = companyService.findById(companyID);
        return userRepository.findAllByCompany(company);
    }


    @Override
    public void deleteEmployeesTimer(List<UUID> ids) {
        List<UUID> notFoundIds = ids.stream()
                .filter(id -> !userRepository.existsUserById(id))
                .toList();

        if (!notFoundIds.isEmpty()) {
            throw new NoDataFoundException(String.format("Пользователи с ID = %s не найдены.", notFoundIds));
        }

        ids.forEach(id -> deletionRequestService.requestDeletion(id, "USER"));
    }


    @Override
    public void cancelEmployeeDeletionTimer(List<UUID> ids) {
        List<UUID> notFoundIds = ids.stream()
                .filter(id -> !userRepository.existsUserById(id))
                .toList();

        if (!notFoundIds.isEmpty()) {
            throw new NoDataFoundException(String.format("Пользователи с ID = %s не найдены.", notFoundIds));
        }

        ids.forEach(id -> deletionRequestService.cancelDeletion(id, "USER"));
    }

}