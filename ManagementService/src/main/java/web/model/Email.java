package web.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Email {
    private UUID id;

    private String name;

    private LocalDateTime createdAt;
}
