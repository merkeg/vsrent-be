package de.merkeg.vsrentbe.request;

import de.merkeg.vsrentbe.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
}
