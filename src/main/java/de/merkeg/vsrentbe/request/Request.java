package de.merkeg.vsrentbe.request;

import de.merkeg.vsrentbe.item.Item;
import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Entity
@Table(name = "requests")
public class Request {

    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;

    @ManyToOne
    @JoinColumn(name = "requested_item", nullable = false)
    Item requestedItem;


    @ManyToOne
    @JoinColumn(name = "requesting_user", nullable = false)
    User requestingUser;
    String notes;
    LocalDate startDate;
    LocalDate endDate;
    RequestState requestState;
    String reviewed_by;

    @Lob
    String review_reason;
}
