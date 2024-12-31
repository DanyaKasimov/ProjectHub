package web.dto.response.email;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailCreateDto {
    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    private String patronymic;

    @NotBlank
    private String domain;
}
