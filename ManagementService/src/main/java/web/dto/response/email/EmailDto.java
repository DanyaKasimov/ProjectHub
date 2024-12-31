package web.dto.response.email;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailDto {
    private UUID id;

    private String name;
}
