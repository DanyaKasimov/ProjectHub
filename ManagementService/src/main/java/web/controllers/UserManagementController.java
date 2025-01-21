package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import web.api.UserManagementApi;
import web.dto.request.SignUpDto;
import web.dto.request.UserEditDto;
import web.dto.response.ResponseDto;
import web.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
//@CrossOrigin("${cors.hosts}")
@RequiredArgsConstructor
public class UserManagementController implements UserManagementApi {

    private final UserService userService;

    @Override
    public ResponseDto addEmployee(final SignUpDto userAddDto) {
        log.info("Поступил запрос на добавление нового сотрудника. Входные данные: {}", userAddDto);

        val data = userService.addEmployee(userAddDto);
        return ResponseDto.builder().result(data).build();
    }

    @Override
    public ResponseDto getEmployee(final UUID id) {
        log.info("Поступил запрос на получение данных сотрудника. ID: {}", id);

        val data = userService.findById(id);
        return ResponseDto.builder().result(data).build();
    }


    @Override
    public ResponseDto getEmployees(final UUID id, final Pageable pageable) {
        log.info("Поступил запрос на получение данных сотрудников определенной компании. ID: {}", id);

        val data = userService.findAllByCompany(id, pageable);
        return ResponseDto.builder().result(data).build();
    }


    @Override
    public ResponseDto updateEmployee(final UserEditDto dto) {
        log.info("Поступил запрос на обновление данных сотрудника. Входные данные: {}", dto);

        userService.updateEmployee(dto);
        return ResponseDto.builder().result("Данные сотрудника обновлены.").build();
    }


    @Override
    public ResponseDto deleteEmployees(final List<UUID> ids) {
        log.info("Поступил запрос на удаление сотрудника. IDs: {}", ids);

        userService.deleteEmployees(ids);
        return ResponseDto.builder().result("Сотрудники удалены").build();
    }


    @Override
    public ResponseDto updatePasswordEmployee(final UUID id) {
        log.info("Поступил запрос на обновление пароля сотрудника. ID: {}", id);

        userService.updatePasswordEmployee(id);
        return ResponseDto.builder().result("Пароль обновлен и отправлен на почту сотрудника.").build();
    }
}
