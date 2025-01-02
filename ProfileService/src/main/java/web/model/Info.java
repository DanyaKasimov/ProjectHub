package web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "info", schema = "profile_management")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(length = 1000)
    private String phoneNumber;

    @Column(length = 300)
    private String address;

    private LocalDate birthday;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}

