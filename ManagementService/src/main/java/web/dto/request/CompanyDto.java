package web.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CompanyDto {

    @Length(min = 1, max = 255, message = "Длина названия должна быть от 1 до 255.")
    private String name;

    @Length(min = 10, max = 10, message = "Длина ИНН должна быть равна 10.")
    private String inn;

    @Length(min = 1, max = 8, message = "Длина домена должна быть от 1 до 8.")
    private String domain;
}
