package web.dto.request;


import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CompanyDto {

    @Length(min = 1, max = 255, message = "Длина названия должна быть от 1 до 255.")
    private String name;

    @Length(min = 10, max = 10, message = "Длина inn должна быть равна 10.")
    private String inn;

    @Length(min = 1, max = 8, message = "Длина domain должна быть от 1 до 8.")
    @Pattern(regexp = "^(?!.*(ООО|ОАО|'))(?!.*['<>])[A-Za-zА-Яа-яЁё0-9\s]+$",
            message = "Название не должно содержать 'ООО', 'ОАО' и кавычек.")
    private String domain;
}
