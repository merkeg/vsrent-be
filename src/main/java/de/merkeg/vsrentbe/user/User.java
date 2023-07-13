package de.merkeg.vsrentbe.user;

import de.merkeg.vsrentbe.auth.RefreshToken;
import de.merkeg.vsrentbe.item.Item;
import de.merkeg.vsrentbe.membership.OrgMembership;
import de.merkeg.vsrentbe.quota.UserQuota;
import de.merkeg.vsrentbe.request.Request;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Log4j2
public class User implements UserDetails {
    @Getter
    @Id
    @GeneratedValue
    @UuidGenerator
    private String id;
    private String username; // Username only present on rwu login
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private boolean locked;
    @Enumerated(EnumType.STRING)
    private UserRegistrationType registrationType;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<OrgMembership> organisations;

    @OneToMany(mappedBy = "requester", fetch = FetchType.EAGER)
    private Set<Request> requests;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    Set<RefreshToken> refreshTokens;

    @OneToMany(mappedBy = "creator")
    Set<Item> createdItems;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserQuota quota;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<SimpleGrantedAuthority> orgAuthorities = this.organisations.stream()
                .map(orgMembership -> orgMembership.getRole().getAuthorities(orgMembership.getOrganisation().getId()))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(role.getAuthorities());
        authorities.addAll(orgAuthorities);

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
