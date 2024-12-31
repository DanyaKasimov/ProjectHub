package web.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Data
public class EmailMessageDto {

    @NotBlank
    private UUID fromId;

    @NotBlank
    private String toName;

    @Length(max = 255, message = "Максимальный размер заголовка - 255 символов")
    private String title;

    @Length(max = 1000, message = "Максимальный размер сообщения - 1000 символов")
    private String body;
}
