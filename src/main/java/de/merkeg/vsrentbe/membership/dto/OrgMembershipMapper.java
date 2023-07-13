package de.merkeg.vsrentbe.membership.dto;

import de.merkeg.vsrentbe.membership.OrgMembership;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrgMembershipMapper {
    public static OrgMembershipMapper INSTANCE = Mappers.getMapper(OrgMembershipMapper.class);

    @Mapping(target = "userId", expression = "java(membership.getUser().getId())")
    @Mapping(target = "orgId", expression = "java(membership.getOrganisation().getId())")
    OrgMembershipDTO orgMembershipToDTO(OrgMembership membership);
}
