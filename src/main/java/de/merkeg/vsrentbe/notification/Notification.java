package de.merkeg.vsrentbe.notification;

import de.merkeg.vsrentbe.notificationprovider.NotificationProvider;
import de.merkeg.vsrentbe.org.Organisation;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalTime;

@Entity
@Table(name = "notification")
public class Notification {

    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;

    int daysBefore;
    LocalTime timeAt;

    @ManyToOne
    @JoinColumn(name = "target", nullable = false)
    NotificationProvider target;
    
}
