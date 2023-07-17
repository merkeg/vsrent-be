package de.merkeg.vsrentbe.confirmation;


import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "confirmations")
public class Confirmation {

    @Id
    String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User targetUser;

    String secretToken;
    String processorData;
    @Enumerated(EnumType.STRING)
    Process process;
}
