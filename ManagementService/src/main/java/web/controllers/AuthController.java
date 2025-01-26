package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import web.api.AuthApi;
import web.dto.request.SignInDto;
import web.dto.response.ResponseDto;
import web.service.AuthenticateService;

@Slf4j
@RestController
//@CrossOrigin("${cors.hosts}")
@RequiredArgsConstructor
public class AuthController implements AuthApi {

    private final AuthenticateService authenticateService;

    @Override
    public ResponseDto signin(SignInDto signInDto) {
        log.info("Поступил запрос на вход. Имя пользователя: {}", signInDto.getUsername());

        String jwt = authenticateService.authenticate(signInDto);
        return ResponseDto.builder().result(jwt).build();
    }
}
