package de.merkeg.vsrentbe.membership;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrgMembershipKey implements Serializable {

    @Column(name = "organisation_id")
    String groupId;

    @Column(name = "user_id")
    String userId;

}
