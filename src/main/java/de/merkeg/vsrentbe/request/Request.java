package de.merkeg.vsrentbe.request;

import de.merkeg.vsrentbe.item.Item;
import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
public class Request {

    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    Item item;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User requester;
    String notes;
    LocalDateTime startDate;
    LocalDateTime endDate;
    RequestState state;
    String reviewed_by;
    boolean returned;
    @Lob
    String reasonining;
}
