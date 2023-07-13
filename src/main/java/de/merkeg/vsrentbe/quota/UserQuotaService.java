package de.merkeg.vsrentbe.quota;

import de.merkeg.vsrentbe.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserQuotaService {
    private final UserQuotaRepository userQuotaRepository;

    public void addToQuota(User user, long size){
        log.debug("Updating quota for user {} with added size '{}'", user.getId(), size);
        UserQuota quota = user.getQuota();
        quota.setUsedQuota(quota.getUsedQuota() + size);
        userQuotaRepository.save(quota);
        log.debug("Done updating quota for user {}", user.getId());
    }

    public void reduceFromQuota(User user, long size) {
        addToQuota(user, -size);
    }

    public void setMaxQuota(User user, long size) {
        UserQuota quota = user.getQuota();
        quota.setMaxQuota(size);
        userQuotaRepository.save(quota);
    }
}
