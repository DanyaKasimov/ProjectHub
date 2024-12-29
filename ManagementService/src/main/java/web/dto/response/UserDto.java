package web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.constants.Role;
import web.model.Company;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID id;

    private String name;

    private String surname;

    private String patronymic;

    private String username;

    private String email;

    private String password;

    private String position;

    private Role role;

    private Company company;

    private LocalDate birthday;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public UserDto withPassword(String password) {
        this.setPassword(password);
        return this;
    }
}
