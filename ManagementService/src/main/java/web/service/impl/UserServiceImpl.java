package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.accessor.EmailService;
import web.dto.request.SignUpDto;
import web.dto.request.UserAddDto;
import web.dto.response.EmailCreateDto;
import web.dto.response.UserDto;
import web.dto.response.UserDataDto;
import web.exception.InvalidDataException;
import web.exception.NoDataFoundException;
import web.mappers.UserMapper;
import web.model.Company;
import web.model.User;
import web.repositories.UserRepository;
import web.service.CompanyService;
import web.service.DeletionRequestService;
import web.service.UserService;
import web.utils.Generator;

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

    private final UserMapper userMapper;

    private final EmailService emailService;

    private final DeletionRequestService deletionRequestService;

    @Override
    @Transactional
    public UserDto addEmployee(final SignUpDto signUpDto) {
        log.info("Добавление нового сотрудника.");

        val username =
                Generator.generateUsername(signUpDto.getName(), signUpDto.getSurname(), signUpDto.getPatronymic());

        val company = companyService.findById(signUpDto.getCompanyId());

        if (userRepository.existsByUsernameAndCompany(username, company)) {
            throw new InvalidDataException("Имя пользователя занято.");
        }

        val domain = companyService.getDomainById(signUpDto.getCompanyId());
        val password = Generator.generatePassword();


        UUID emailId = UUID.fromString(String.valueOf(emailService.createEmail(
                EmailCreateDto.builder()
                        .name(signUpDto.getName())
                        .surname(signUpDto.getSurname())
                        .patronymic(signUpDto.getPatronymic())
                        .domain(domain)
                        .build()
        ).getResult()));


        val user = userRepository.save(
                User.builder()
                        .name(signUpDto.getName())
                        .surname(signUpDto.getSurname())
                        .patronymic(signUpDto.getPatronymic())
                        .username(username)
                        .password(passwordEncoder.encode(password))
                        .position(signUpDto.getPosition())
                        .emailId(emailId)
                        .company(company)
                        .role(signUpDto.getRole())
                        .birthday(signUpDto.getBirthday())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        return userMapper.toDto(user).withPassword(password);
    }


    @Override
    @Transactional
    public void updateEmployee(final UserAddDto dto) {
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
        user.setBirthday(dto.getBirthday());

        userRepository.save(user);
    }


    @Override
    @Transactional
    public String updatePasswordEmployee(final UUID id) {
        log.info("Обновление пароля сотрудника.");

        val user = userRepository.findById(id).orElseThrow(
                () -> new NoDataFoundException("Сотрудник не найден.")
        );

        val password = Generator.generatePassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        return password;
    }


    @Override
    @Transactional
    public void deleteEmployees(final List<UUID> ids) {
        log.info("Удаление сотрудников начато.");

        ids.forEach((id) -> {
            userRepository.delete(
                    userRepository
                            .findById(id)
                            .orElseThrow(() -> new NoDataFoundException(
                                    String.format("Сотрудник c ID = %s не найден.", id)
                            )));
        });

        log.info("Сотрудники удалены.");

    }


    @Override
    public UserDataDto findByUsername(final String username) {
        val user = userRepository.findByUsername(username).orElseThrow(
                () -> new NoDataFoundException("Пользователь не найден.")
        );
        return userMapper.toDtoLite(user);
    }

    @Override
    public List<UUID> findAllIdsByCompany(UUID companyID) {
        Company company = companyService.findById(companyID);
        return userRepository.findAllByCompany(company);
    }


    @Override
    public void deleteEmployeesTimer(List<UUID> ids) {
        for (UUID id : ids) {
            if (!userRepository.existsUserById(id)) {
                throw new NoDataFoundException(String.format("Пользователь с ID = %s не найден.", id));
            }
        }
        for (UUID id : ids) {
            deletionRequestService.requestDeletion(id, "USER");
        }
    }


    @Override
    public void cancelEmployeeDeletionTimer(List<UUID> ids) {
        for (UUID id : ids) {
            if (!userRepository.existsUserById(id)) {
                throw new NoDataFoundException(String.format("Пользователь с ID = %s не найден.", id));
            }
        }
        for (UUID id : ids) {
            deletionRequestService.cancelDeletion(id, "USER");
        }
    }

}