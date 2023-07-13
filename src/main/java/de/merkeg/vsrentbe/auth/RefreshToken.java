package de.merkeg.vsrentbe.auth;

import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;
    String refreshToken;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    User owner;

    LocalDateTime creationTime;
    LocalDateTime lastRefresh;
}
