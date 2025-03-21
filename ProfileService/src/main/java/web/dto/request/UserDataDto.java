package web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDto {

    private UUID id;

    private String name;

    private String surname;

    private String patronymic;

    private String username;

    private String email;

    private String position;

    private String role;

    private Company company;
}