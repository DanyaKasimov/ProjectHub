package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.RestController;
import web.api.EmailApi;
import web.dto.request.EmailCreateDto;
import web.dto.response.EmailSendDto;
import web.dto.response.ResponseDto;
import web.service.EmailService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController implements EmailApi {

    private final EmailService emailService;

    @Override
    public ResponseDto createEmail(final EmailCreateDto emailCreateDto) {
        log.info("Поступил запрос на создание электронной почты. Входные данные: {}", emailCreateDto);

        val data = emailService.createEmail(emailCreateDto);
        return ResponseDto.builder().result(data).build();
    }

    @Override
    public ResponseDto sendEmail(final EmailSendDto sendDto) {
        log.info("Поступил запрос на отправку письма на электронную почту. Address: {}", sendDto.getAddress());

        emailService.sendEmail(sendDto);
        return ResponseDto.builder().result("Письмо отправлено.").build();
    }
}
