package de.merkeg.vsrentbe.quota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuotaRepository extends JpaRepository<UserQuota, String> {
}
