package de.merkeg.vsrentbe.confirmation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationRepository extends JpaRepository<Confirmation, String> {

}
