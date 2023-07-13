package de.merkeg.vsrentbe.item;

import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.request.Request;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Entity
@Table(name = "items")
public class Item {

    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;

    @Getter
    String name;

    @Lob
    @Getter
    String description;

    @Getter
    Integer quantity;

    @Getter
    String imageHandle;

    @ManyToOne
    @JoinColumn(name = "organisation_id", nullable = false)
    Organisation organisation;

    @OneToMany(mappedBy = "requestedItem")
    Set<Request> requests;
}
