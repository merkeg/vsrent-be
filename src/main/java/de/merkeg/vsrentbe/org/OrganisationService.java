package de.merkeg.vsrentbe.org;

import de.merkeg.vsrentbe.exception.OrgNotFoundException;
import de.merkeg.vsrentbe.exception.UserNotInOrganisationException;
import de.merkeg.vsrentbe.membership.OrgMembership;
import de.merkeg.vsrentbe.membership.OrgMembershipRepository;
import de.merkeg.vsrentbe.membership.OrgRole;
import de.merkeg.vsrentbe.org.dto.OrgCreationRequestDTO;
import de.merkeg.vsrentbe.org.dto.OrgInfoDTO;
import de.merkeg.vsrentbe.user.User;
import de.merkeg.vsrentbe.util.Base58;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final OrgMembershipRepository orgMembershipRepository;


    public OrgInfoDTO createOrganisation(User owner, OrgCreationRequestDTO creationRequestDTO){
        Organisation org = Organisation.builder()
                .id(Base58.uuid58())
                .name(creationRequestDTO.getName())
                .description(creationRequestDTO.getDescription())
                .imageHandle(creationRequestDTO.getImageHandle()).build();
        organisationRepository.save(org);

        OrgMembership membership = new OrgMembership(owner, org, OrgRole.ROLE_ADMIN);
        orgMembershipRepository.save(membership);

        return OrganisationMapper.INSTANCE.orgToInfoDTO(org);
    }

    public OrgInfoDTO getOrgInfo(String orgId) {
        return OrganisationMapper.INSTANCE.orgToInfoDTO(getOrg(orgId));
    }

    public Organisation getOrg(String orgId) {
        Optional<Organisation> org = organisationRepository.findById(orgId);
        if(org.isEmpty()) {
            throw new OrgNotFoundException();
        }
        return org.get();
    }

    public void deleteOrganisation(Organisation organisation) {
        orgMembershipRepository.deleteAll(organisation.getMembers());
        organisationRepository.delete(organisation);
    }

    public void leaveOrganisation(Organisation org, User user) {
        OrgMembership membership = findMembership(org, user);
        orgMembershipRepository.delete(membership);
    }

    public OrgMembership findMembership(Organisation organisation, User user) {
        Optional<OrgMembership> membership = organisation.getMembers().stream().filter(p -> p.getUser() == user).findAny();
        if(membership.isEmpty()) {
            throw new UserNotInOrganisationException();
        }
        return membership.get();
    }



}
