package web.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.dto.request.CompanyDto;
import web.dto.response.ResponseDto;

import java.util.UUID;

@RequestMapping("api/v1/company")
@Tag(name = "Управление компаниями", description = "Методы для управления компаниями.")
public interface CompanyApi {

    @Operation(description = "Регистрация компании.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Регистрация прошла успешно.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "ИНН или Домен уже заняты.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto addCompany(final @RequestBody @Valid CompanyDto companyDto);



    @Operation(description = "Удаление компании.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удаление прошло успешно.",
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
                    description = "Компания не найдена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto deleteCompany(final @PathVariable @Valid UUID id);


    @Operation(description = "Отмена удаления компании.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Отмена прошла успешно.",
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
                    description = "Компания не найдена.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PutMapping("/delete-cancel/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto deleteCancelCompany(final @PathVariable @Valid UUID id);
}
