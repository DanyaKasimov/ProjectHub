package web.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class InfoDto {

    private UUID id;

    @NotBlank(message = "Обязательное поле user_id")
    private UUID userId;

    private String phoneNumber;

    private String address;

    private LocalDate birthday;
}
