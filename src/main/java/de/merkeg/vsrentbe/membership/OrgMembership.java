package de.merkeg.vsrentbe.membership;

import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
public class OrgMembership {

    @EmbeddedId
    OrgMembershipKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")
    @JoinColumn(name = "organisation_id")
    Organisation organisation;

    @Enumerated(EnumType.STRING)
    OrgRole role;

    public OrgMembership(User user, Organisation org, OrgRole role) {

        this.id = new OrgMembershipKey(org.getId(), user.getId());
        this.user = user;
        this.organisation = org;
        this.role = role;
    }
}
