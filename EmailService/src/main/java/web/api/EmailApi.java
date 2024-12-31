package web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import web.dto.request.EmailCreateDto;
import web.dto.response.EmailSendDto;
import web.dto.response.ResponseDto;

import java.util.UUID;

@RequestMapping("api/v1/email")
@Tag(name = "Управление электронной почтой", description = "Методы для управления электронной почтой")
public interface EmailApi {

    @Operation(description = "Создание электронной почты сотрудника")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Почта создана успешно.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto createEmail(final @RequestBody @Valid EmailCreateDto signInDto);


    @Operation(description = "Отправка письма на электронную почту")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Письмо отправлено.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto sendEmail(final @RequestBody @Valid EmailSendDto sendDto);


    @Operation(description = "Получение почты по ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Почта получена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Недостаточно прав.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Почта не найдена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @GetMapping("/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getEmail(final @PathVariable @Valid UUID id);
}

