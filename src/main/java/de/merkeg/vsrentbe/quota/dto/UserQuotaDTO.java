package de.merkeg.vsrentbe.quota.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserQuotaDTO {
    String usedQuota;
    String maxQuota;
}
