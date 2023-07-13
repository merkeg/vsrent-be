package de.merkeg.vsrentbe.notification;

import de.merkeg.vsrentbe.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
}
