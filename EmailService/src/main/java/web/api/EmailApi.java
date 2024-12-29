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
import web.dto.request.EmailCreateDto;
import web.dto.response.ResponseDto;

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

}
