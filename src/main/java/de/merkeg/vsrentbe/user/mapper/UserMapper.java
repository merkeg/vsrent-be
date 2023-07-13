package de.merkeg.vsrentbe.user.mapper;

import de.merkeg.vsrentbe.membership.OrgMembership;
import de.merkeg.vsrentbe.membership.dto.OrgMembershipDTO;
import de.merkeg.vsrentbe.membership.dto.OrgMembershipMapper;
import de.merkeg.vsrentbe.quota.UserQuota;
import de.merkeg.vsrentbe.quota.dto.UserQuotaDTO;
import de.merkeg.vsrentbe.user.User;
import de.merkeg.vsrentbe.user.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.GrantedAuthority;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collection;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "authorities", target = "authorities", qualifiedByName = "authorityMapper")
    @Mapping(source = "organisations", target = "organisations", qualifiedByName = "organisationsMapper")
    @Mapping(source = "quota", target = "quota", qualifiedByName = "quotaMapper")
    UserDTO userToInfoDTO(User user);

    @Named("authorityMapper")
    public static Set<String> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }
    @Named("organisationsMapper")
    public static Set<OrgMembershipDTO> mapOrgs(Set<OrgMembership> memberships) {
        if(memberships == null) return Set.of();
        return memberships.stream().map(OrgMembershipMapper.INSTANCE::orgMembershipToDTO).collect(Collectors.toSet());
    }

    @Named("quotaMapper")
    public static UserQuotaDTO mapQuota(UserQuota quota) {
        if (quota == null) UserQuotaDTO.builder().build();

        return UserQuotaDTO.builder()
                .usedQuota(readableFileSize(quota.getUsedQuota()))
                .maxQuota(readableFileSize(quota.getMaxQuota()))
                .build();
    }

    public static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#", DecimalFormatSymbols.getInstance(Locale.US)).format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
