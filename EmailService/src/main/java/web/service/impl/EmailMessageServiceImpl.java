package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.dto.request.EmailMessageDto;
import web.dto.response.email.EmailDto;
import web.exception.NoDataFoundException;
import web.model.EmailMessage;
import web.repositories.EmailMessageRepository;
import web.service.EmailMessageService;
import web.service.EmailService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailMessageServiceImpl implements EmailMessageService {

    private final EmailMessageRepository emailMessageRepository;

    private final EmailService emailService;


    // TODO Добавить отправку уведомления в NotificationService
    @Override
    public void sendWorkEmail(final EmailMessageDto dto) {
        log.info("Отправка сообщения на кооперативную почту. Адрес: {}", dto.getToName());

        EmailDto emailFrom = emailService.findById(dto.getFromId());
        EmailDto emailTo = emailService.findByName(dto.getToName());

        emailMessageRepository.saveAndFlush(
                EmailMessage.builder()
                        .toId(emailTo.getId())
                        .fromId(emailFrom.getId())
                        .title(dto.getTitle())
                        .body(dto.getBody())
                        .createdAt(LocalDateTime.now())
                        .build()
        );


    }


    @Override
    public Page<EmailMessage> getEmailList(final UUID id, final Pageable pageable) {
        log.info("Получение списка писем. ID: {}", id);

        emailService.findById(id);
        return emailMessageRepository.findAllByToId(id, pageable);
    }


    @Override
    public EmailMessage findById(final UUID id) {
        log.info("Получение письма. ID: {}", id);

        return emailMessageRepository.findById(id).orElseThrow(() ->
                new NoDataFoundException(String.format("Письмо с ID = %s не найдено", id)));
    }

}
