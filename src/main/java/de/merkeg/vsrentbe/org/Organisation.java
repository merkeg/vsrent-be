package de.merkeg.vsrentbe.org;

import de.merkeg.vsrentbe.item.Item;
import de.merkeg.vsrentbe.membership.OrgMembership;
import de.merkeg.vsrentbe.notification.Notification;
import de.merkeg.vsrentbe.notification.NotificationType;
import de.merkeg.vsrentbe.notificationprovider.NotificationProvider;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "organisations")
@EqualsAndHashCode(exclude="members")
public class Organisation {
    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    String id;
    String name;
    String description;
    String imageHandle;

    @OneToMany(mappedBy = "organisation")
    Set<Item> items;

    @OneToMany(mappedBy = "organisation")
    Set<OrgMembership> members;

    @OneToMany(mappedBy = "target")
    Set<NotificationProvider> notificationTargets;
}
