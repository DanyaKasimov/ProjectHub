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
import web.dto.request.InfoDto;
import web.dto.response.ResponseDto;


import java.util.UUID;

@RequestMapping("api/v1/profile")
@Tag(name = "Данные сотрудников", description = "Методы для предоставления данных сотрудников")
public interface ProfileApi {

    @Operation(description = "Предоставление данных сотрудника")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные предоставлены.",
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
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getInfo(final @PathVariable @Valid UUID id);



    @Operation(description = "Добавление данных сотрудника")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные добавлены.",
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
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto addInfo(final @RequestBody @Valid InfoDto dto);



    @Operation(description = "Обновление данных сотрудника")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные обновлены.",
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
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            )
    })
    @PostMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto updateInfo(final @RequestBody @Valid InfoDto dto);
}
