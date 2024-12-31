package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import web.api.EmailApi;
import web.dto.request.EmailCreateDto;
import web.dto.request.EmailMessageDto;
import web.dto.response.email.EmailSendDto;
import web.dto.response.ResponseDto;
import web.service.EmailMessageService;
import web.service.EmailService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailController implements EmailApi {

    private final EmailService emailService;

    private final EmailMessageService emailMessageService;

    @Override
    public ResponseDto createEmail(final EmailCreateDto emailCreateDto) {
        log.info("Поступил запрос на создание электронной почты. Входные данные: {}", emailCreateDto);

        val data = emailService.createEmail(emailCreateDto);
        return ResponseDto.builder().result(data).build();
    }


    @Override
    public ResponseDto sendEmail(final EmailSendDto sendDto) {
        log.info("Поступил запрос на отправку письма на электронную почту. Адрес: {}", sendDto.getAddress());

        emailService.sendEmail(sendDto);
        return ResponseDto.builder().result("Письмо отправлено.").build();
    }


    @Override
    public ResponseDto getEmail(final UUID id) {
        log.info("Поступил запрос на получение названия электронной почты. ID: {}", id);

        val email = emailService.findById(id);
        return ResponseDto.builder().result(email.getName()).build();
    }


    @Override
    public ResponseDto sendWorkEmail(final EmailMessageDto dto) {
        log.info("Поступил запрос на отправку сообщения на кооперативную почту. Адрес: {}", dto.getToName());

        emailMessageService.sendWorkEmail(dto);
        return ResponseDto.builder().result("Письмо отправлено.").build();
    }


    @Override
    public ResponseDto getEmailList(final UUID id, final Pageable pageable) {
        log.info("Поступил запрос на получения списка писем с пагинацией. Владелец: {}", id);

        val list = emailMessageService.getEmailList(id, pageable);
        return ResponseDto.builder().result(list).build();
    }
}
