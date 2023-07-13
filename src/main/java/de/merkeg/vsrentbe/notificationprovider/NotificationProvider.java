package de.merkeg.vsrentbe.notificationprovider;

import de.merkeg.vsrentbe.notification.Notification;
import de.merkeg.vsrentbe.notification.NotificationType;
import de.merkeg.vsrentbe.org.Organisation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification_provider")
public class NotificationProvider {
    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;

    NotificationType notificationType;

    String recipient;

    @ManyToOne
    @JoinColumn(name = "target", nullable = false)
    Organisation target;

    @OneToMany(mappedBy = "target")
    Set<Notification> notifications;
}
