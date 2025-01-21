package web.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import web.constants.Role;

import java.util.UUID;

@Data
public class UserEditDto {
    private UUID id;

    @Length(min = 1, max = 255, message = "Длина имени должна быть от 1 до 255.")
    private String name;

    @Length(min = 1, max = 255, message = "Длина фамилии должна быть от 1 до 255.")
    private String surname;

    @Length(min = 1, max = 255, message = "Длина отчества должна быть от 1 до 255.")
    private String patronymic;

    @Length(min = 3, max = 150, message = "Длина должности должна быть от 3 до 150.")
    private String position;

    private UUID companyId;

    private Role role;
}
