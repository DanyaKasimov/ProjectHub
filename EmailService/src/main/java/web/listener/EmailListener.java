package web.listener;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import web.dto.response.email.EmailSendDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailListener {

    private final JavaMailSender emailSender;

    @KafkaListener(topics = "${spring.kafka.topic.send-email}", groupId = "email_group")
    public void listenEmailTopic(final EmailSendDto dto) {
        log.info("Пришел запрос из кафки на отправку письма");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.getAddress());
        message.setSubject("ProjectHub");
        message.setText(String.valueOf(dto.getContent()));

        emailSender.send(message);

        log.info("Письмо отправлено адресату: {}", dto.getAddress());
    }
}