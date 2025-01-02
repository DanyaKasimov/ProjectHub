package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import web.api.ProfileApi;
import web.dto.request.InfoDto;
import web.dto.response.ResponseDto;


import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileApi {

    @Override
    public ResponseDto getInfo(final UUID id) {
        log.info("Поступил запрос на получение данных пользователя. ID: {}", id);

        return null;
    }

    @Override
    public ResponseDto addInfo(final InfoDto dto) {
        log.info("Поступил запрос на добавление данных пользователя. Входные данные: {}", dto);

        return null;
    }

    @Override
    public ResponseDto updateInfo(final InfoDto dto) {
        log.info("Поступил запрос на обновление данных пользователя. Входные данные: {}", dto);

        return null;
    }


}
