package de.merkeg.vsrentbe.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {

    ROLE_USER(Set.of(
            Permission.SELF_AUTH_INFO,
            Permission.ORG_LIST,
            Permission.ORG_INFO,
            Permission.MEDIA_UPLOAD,
            Permission.MEDIA_DELETE,
            Permission.ITEM_LIST
    )),
    ROLE_MANAGER(Set.of(

    )),
    ROLE_ADMIN(Set.of(
            Permission.SELF_AUTH_INFO,
            Permission.ORG_CREATE,
            Permission.ORG_LIST,
            Permission.ORG_INFO,
            Permission.ORG_DELETE,
            Permission.ADMIN_ORG_MANAGE,
            Permission.MEDIA_UPLOAD,
            Permission.MEDIA_DELETE,
            Permission.ITEM_LIST
    ));
    @Getter
    private final Set<Permission> permissions;


    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority(this.name()));
        return authorities;
    }
}
