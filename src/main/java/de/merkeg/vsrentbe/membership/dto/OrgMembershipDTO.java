package de.merkeg.vsrentbe.membership.dto;

import de.merkeg.vsrentbe.membership.OrgRole;
import lombok.Data;

@Data
public class OrgMembershipDTO {
    private String userId;
    private String orgId;
    private OrgRole role;
}
