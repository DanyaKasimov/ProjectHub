package web.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import web.dto.request.EmailMessageDto;
import web.dto.response.email.EmailDto;
import web.model.EmailMessage;
import web.repositories.EmailMessageRepository;
import web.service.EmailMessageService;
import web.service.EmailService;

import java.time.LocalDateTime;

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
}
