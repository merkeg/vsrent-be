package de.merkeg.vsrentbe.item;

import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.request.Request;
import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {

    @Id
    String id;
    String name;
    String description;
    Integer quantity;
    String imageHandle;
    boolean enabled;


    @ManyToOne
    @JoinColumn(name = "organisation_id", nullable = false)
    Organisation organisation;

    @OneToMany(mappedBy = "item")
    Set<Request> requests;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User creator;
}
