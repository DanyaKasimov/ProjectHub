package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import web.dto.request.EmailCreateDto;
import web.dto.response.email.EmailSendDto;
import web.dto.response.email.EmailDto;
import web.exception.InvalidDataException;
import web.exception.NoDataFoundException;
import web.mappers.EmailMapper;
import web.model.Email;
import web.repositories.EmailRepository;
import web.service.EmailService;
import web.utils.Generator;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    private final KafkaTemplate<String, EmailSendDto> kafkaTemplate;

    @Value("${spring.kafka.topic.send-email}")
    private String topic;

    @Override
    public EmailDto createEmail(final EmailCreateDto dto) {
        val emailName =
                Generator.generateEmail(dto.getName(), dto.getSurname(), dto.getPatronymic(), dto.getDomain());

        if (emailRepository.existsByName(emailName)) {
            throw new InvalidDataException("Название электронной почты занято. Повторите попытку.");
        }

        val email = emailRepository.saveAndFlush(
                Email.builder()
                        .name(emailName)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return emailMapper.toDto(email);
    }

    @Override
    public void sendEmail(final EmailSendDto dto) {
        log.info("Отправка письма в кафку.");

        kafkaTemplate.send(topic, dto);
    }


    @Override
    public EmailDto findById(final UUID id) {
        Email email = emailRepository.findById(id).orElseThrow(() ->
                new NoDataFoundException(String.format("Email с ID = %s не найден", id)));
        return emailMapper.toDto(email);
    }


    @Override
    public EmailDto findByName(final String name) {
        Email email = emailRepository.findByName(name).orElseThrow(() ->
                new NoDataFoundException(String.format("Электронная почта с именем %s не найдена.", name)));
        return emailMapper.toDto(email);
    }
}
