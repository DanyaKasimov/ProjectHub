package web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import web.dto.request.SignInDto;
import web.dto.response.ResponseDto;

@RequestMapping("api/v1/auth")
@Tag(name = "Авторизация сотрудников", description = "Методы для авторизации.")
public interface AuthApi {

    @Operation(description = "Вход сотрудника в аккаунт.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Вход прошел успешно.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверные данные пользователя.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/signin")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto signin(final @RequestBody @Valid SignInDto signInDto);

}
