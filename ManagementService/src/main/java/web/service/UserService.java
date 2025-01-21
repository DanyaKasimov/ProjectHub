package web.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.dto.request.SignUpDto;
import web.dto.request.UserEditDto;
import web.dto.response.UserDto;
import web.dto.response.UserDataDto;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    UserDto addEmployee(final SignUpDto dto);

    void updateEmployee(final UserEditDto dto);

    void deleteEmployees(final List<UUID> ids);

    void updatePasswordEmployee(final UUID id);

    UserDataDto findByUsername(final String username);

    UserDataDto findById(final UUID id);

    Page<UserDataDto> findAllByCompany(final UUID id, final Pageable pageable);

    List<UUID> findAllIdsByCompany(final UUID companyID);

    void deleteEmployeesTimer(List<UUID> ids);

    void cancelEmployeeDeletionTimer(List<UUID> ids);

}
