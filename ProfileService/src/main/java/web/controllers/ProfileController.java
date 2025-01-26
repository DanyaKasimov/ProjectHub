package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.RestController;
import web.api.ProfileApi;
import web.dto.request.InfoDto;
import web.dto.response.ResponseDto;
import web.service.InfoService;


import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController implements ProfileApi {

    private final InfoService infoService;

    @Override
    public ResponseDto getEmployeeData(final String id) {
        log.info("Поступил запрос на получение данных пользователя. ID: {}", id);

        val currentId = id.equals("current") ? infoService.getCurrentId() : UUID.fromString(id);
        val info = infoService.findById(currentId);
        return ResponseDto.builder().result(info).build();
    }


    @Override
    public ResponseDto addInfo(final InfoDto dto) {
        log.info("Поступил запрос на добавление данных пользователя. Входные данные: {}", dto);

        val info = infoService.addInfo(dto);
        return ResponseDto.builder().result(info).build();
    }


    @Override
    public ResponseDto updateInfo(final InfoDto dto) {
        log.info("Поступил запрос на обновление данных пользователя. Входные данные: {}", dto);

        val info = infoService.updateInfo(dto);
        return ResponseDto.builder().result(info).build();
    }


}
