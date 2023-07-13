package de.merkeg.vsrentbe.user.dto;

import de.merkeg.vsrentbe.membership.dto.OrgMembershipDTO;
import de.merkeg.vsrentbe.quota.dto.UserQuotaDTO;
import de.merkeg.vsrentbe.user.Role;
import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {

    private String id;
    private String username;
    private String fullName;
    private String phoneNumber;
    private Role role;
    private Set<String> authorities;
    private Set<OrgMembershipDTO> organisations;
    private UserQuotaDTO quota;

}
