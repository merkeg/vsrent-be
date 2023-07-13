package de.merkeg.vsrentbe.org.dto;

import de.merkeg.vsrentbe.item.Item;
import de.merkeg.vsrentbe.membership.OrgMembership;
import de.merkeg.vsrentbe.membership.dto.OrgMembershipDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrgInfoDTO {

    String id;
    String name;
    String description;
    String imageHandle;
    Set<Item> items;
    Set<OrgMembershipDTO> members;
}
