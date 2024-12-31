package web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "email_messages", schema = "email_management")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMessage {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private UUID fromId;

    @Column(nullable = false)
    private UUID toId;

    private String title;

    @Column(length = 1000)
    private String body;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
