package web.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.dto.request.SignUpDto;
import web.dto.request.UserEditDto;
import web.dto.response.ResponseDto;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/user-management")
@Tag(name = "Управление сотрудниками", description = "Методы для управления.")
public interface UserManagementApi {

    @Operation(description = "Добавление сотрудника.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Добавление прошло успешно.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Пользователь уже существует.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto addEmployee(final @RequestBody @Valid SignUpDto userAddDto);


    @Operation(description = "Получение данных сотрудника.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены успешно.",
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
            ),
    })
    @GetMapping("/get-user/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getEmployee(final @PathVariable @Valid UUID id);


    @Operation(description = "Получение списка сотрудников определенной компании.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Данные получены успешно.",
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
    @GetMapping("/company-list/{id}")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto getEmployees(final @PathVariable @Valid UUID id,
                             @Parameter(description = "Пагинация списка.", required = true)
                             @ParameterObject @PageableDefault Pageable pageable );


    @Operation(description = "Обновление сотрудника.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Обновление прошло успешно.",
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
            ),
    })
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto updateEmployee(final @RequestBody @Valid UserEditDto dto);



    @Operation(description = "Удаление сотрудников.")
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
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @DeleteMapping("/delete/{ids}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto deleteEmployees(final @PathVariable @Valid List<UUID> ids);


    @Operation(description = "Удаление сотрудника.")
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
                    description = "Пользователь не найден.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
    })
    @PutMapping("/update-password/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    ResponseDto updatePasswordEmployee(final @PathVariable @Valid UUID id);

}
