package web.service;

import org.springframework.stereotype.Service;
import web.dto.request.SignUpDto;
import web.dto.request.UserAddDto;
import web.dto.response.UserDto;
import web.dto.response.UserDataDto;
import web.model.User;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserDto addEmployee(final SignUpDto dto);

    void updateEmployee(final UserAddDto dto);

    void deleteEmployees(final List<UUID> ids);

    String updatePasswordEmployee(final UUID id);

    UserDataDto findByUsername(final String username);

    List<UUID> findAllIdsByCompany(final UUID companyID);

    void deleteEmployeesTimer(List<UUID> ids);

    void cancelEmployeeDeletionTimer(List<UUID> ids);

}
