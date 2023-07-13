package de.merkeg.vsrentbe.org;


import de.merkeg.vsrentbe.item.Item;
import de.merkeg.vsrentbe.item.dto.ItemDTO;
import de.merkeg.vsrentbe.item.dto.ItemMapper;
import de.merkeg.vsrentbe.membership.OrgMembership;
import de.merkeg.vsrentbe.membership.dto.OrgMembershipDTO;
import de.merkeg.vsrentbe.membership.dto.OrgMembershipMapper;
import de.merkeg.vsrentbe.org.dto.OrgInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface OrganisationMapper {

    public static OrganisationMapper INSTANCE = Mappers.getMapper(OrganisationMapper.class);


    @Mapping(source = "members", target = "members", qualifiedByName = "organisationsMapper")
    @Mapping(source = "items", target = "items", qualifiedByName = "itemsMapper")
    OrgInfoDTO orgToInfoDTO(Organisation org);

    @Named("organisationsMapper")
    public static Set<OrgMembershipDTO> mapOrgs(Set<OrgMembership> memberships) {
        if(memberships == null) return Set.of();
        return memberships.stream().map(OrgMembershipMapper.INSTANCE::orgMembershipToDTO).collect(Collectors.toSet());
    }

    @Named("itemsMapper")
    public static Set<ItemDTO> mapItems(Set<Item> items) {
        return items.stream().map(ItemMapper.INSTANCE::mapToDTO).collect(Collectors.toSet());
    }
}
