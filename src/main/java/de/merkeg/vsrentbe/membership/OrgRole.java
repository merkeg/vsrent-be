package de.merkeg.vsrentbe.membership;

import de.merkeg.vsrentbe.exception.OrgNoPermissionException;
import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.user.Permission;
import de.merkeg.vsrentbe.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Log4j2
public enum OrgRole {
    ROLE_MEMBER(Set.of(
         OrgPermission.CALENDAR_VIEW,
         OrgPermission.INVENTORY_VIEW
    )),
    ROLE_MANAGER(Set.of()),
    ROLE_ADMIN(Set.of(
            OrgPermission.USER_ADD,
            OrgPermission.USER_ROLE,
            OrgPermission.USER_DEL,
            OrgPermission.INVENTORY_VIEW,
            OrgPermission.INVENTORY_ADD,
            OrgPermission.INVENTORY_MODIFY,
            OrgPermission.INVENTORY_DELETE
    ));

    @Getter
    private final Set<OrgPermission> permissions;


    public Set<SimpleGrantedAuthority> getAuthorities(String prefix) {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(prefix+":"+permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority(prefix+":"+this.name()));
        return authorities;
    }

    public static boolean hasOrgPermission(User user, Organisation organisation, OrgPermission permission){
        String permissionString = organisation.getId() + ":" + permission.getPermission();
        Optional<? extends GrantedAuthority> authority = user.getAuthorities().stream()
                .filter(grantedAuthority -> grantedAuthority.getAuthority().equals(permissionString)).findAny();
        return authority.isPresent();
    }

    public static void throwOnNoOrgPermission(User user, Organisation organisation, OrgPermission permission) {
        if(!hasOrgPermission(user, organisation, permission)) {
            throw new OrgNoPermissionException();
        }
    }
}
