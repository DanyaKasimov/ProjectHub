package web.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class EmailDto {
    private UUID id;

    private String name;
}
