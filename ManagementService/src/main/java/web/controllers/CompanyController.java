package web.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.web.bind.annotation.RestController;
import web.api.CompanyApi;
import web.dto.request.CompanyDto;
import web.dto.response.ResponseDto;
import web.exception.InvalidDataException;
import web.service.CompanyService;
import web.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
//@CrossOrigin("${cors.hosts}")
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

    private final CompanyService companyService;

    private final UserService userService;

    @Override
    public ResponseDto addCompany(final CompanyDto companyDto) {
        log.info("Поступил запрос на регистрацию компании. Входные данные: {}", companyDto);

        val companyId = companyService.saveCompany(companyDto);
        return ResponseDto.builder().result(companyId).build();
    }

    @Override
    public ResponseDto deleteCompany(final UUID id) {
        log.info("Поступил запрос на удаление компании. ID: {}", id);

        List<UUID> usersIds = userService.findAllIdsByCompany(id);
        userService.deleteEmployeesTimer(usersIds);
        companyService.requestCompanyDeletion(id);
        return ResponseDto.builder().result("Запрос на удаление компании прошел успешно.").build();
    }

    @Override
    public ResponseDto deleteEmptyCompany(final UUID id) {
        log.info("Поступил запрос на удаление пустой компании. ID: {}", id);

        List<UUID> usersIds = userService.findAllIdsByCompany(id);
        if (!usersIds.isEmpty()) {
            throw new InvalidDataException("Удаление отменено. В компании зарегистрированы сотрудник(-и).");
        }
        companyService.deleteCompany(id);
        return ResponseDto.builder().result("Компания удалена успешно.").build();
    }

    @Override
    public ResponseDto deleteCancelCompany(final UUID id) {
        log.info("Поступил запрос на отмену удаления компании. ID: {}", id);

        List<UUID> usersIds = userService.findAllIdsByCompany(id);
        userService.cancelEmployeeDeletionTimer(usersIds);
        companyService.cancelCompanyDeletion(id);
        return ResponseDto.builder().result("Запрос на удаление компании отменен.").build();
    }
}
