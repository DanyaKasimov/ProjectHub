package web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "deletion_requests", schema = "user_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeletionRequest {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private UUID entityId;

    @Column(nullable = false)
    private String entityType; // "USER" или "COMPANY"

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private boolean canceled;
}