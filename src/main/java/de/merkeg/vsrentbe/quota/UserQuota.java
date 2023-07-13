package de.merkeg.vsrentbe.quota;

import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "user_quota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQuota {
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long usedQuota;
    private long maxQuota;

}
