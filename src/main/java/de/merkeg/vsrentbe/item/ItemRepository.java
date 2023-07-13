package de.merkeg.vsrentbe.item;

import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {
    List<Item> findAllByOrganisation(String organisation, Pageable pageable);

}
