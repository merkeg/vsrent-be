package de.merkeg.vsrentbe.org;

import de.merkeg.vsrentbe.membership.OrgPermission;
import de.merkeg.vsrentbe.membership.OrgRole;
import de.merkeg.vsrentbe.org.dto.OrgCreationRequestDTO;
import de.merkeg.vsrentbe.org.dto.OrgInfoDTO;
import de.merkeg.vsrentbe.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/org")
@Tag(name = "Organisation")
@RequiredArgsConstructor
public class OrgResource {

    private final OrganisationService organisationService;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('org:create')")
    public OrgInfoDTO createOrg(@Valid @RequestBody OrgCreationRequestDTO creationRequestDTO) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return organisationService.createOrganisation(user, creationRequestDTO);
    }

    @GetMapping("/{orgId}")
    @PreAuthorize("hasAuthority('org:info')")
    public ResponseEntity<OrgInfoDTO> getOrgInfo(@PathVariable String orgId) {
        return ResponseEntity.ok(organisationService.getOrgInfo(orgId));
    }

    @GetMapping("/list")
    public OrgInfoDTO[] getOrgs() {
        // #TODO
        return null;
    }

    @PutMapping("/{orgId}")
    @PreAuthorize("isAuthenticated()")
    public void editOrg(@PathVariable String orgId){
        // TODO
    }

    @DeleteMapping("/{orgId}")
    @PreAuthorize("isAuthenticated()")
    public void deleteOrg(Authentication authentication, @PathVariable String orgId) {
        User user = (User) authentication.getPrincipal();
        Organisation organisation = organisationService.getOrg(orgId);
        OrgRole.throwOnNoOrgPermission(user, organisation, OrgPermission.ORG_DELETE);

        organisationService.deleteOrganisation(organisation);
    }

    @PostMapping("/{orgId}/members")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Invite member to the organisation")
    public void inviteMember(@PathVariable String orgId){

    }

    @PutMapping("/{orgId}/members/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Set role of a member")
    public void setMemberRole(@PathVariable String orgId, @PathVariable String userId) {

    }

    @DeleteMapping("/{orgId}/members/{userId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Remove a member from the organisation")
    public void removeMember(@PathVariable String orgId, @PathVariable String userId) {

    }

    @PostMapping("/{orgId}/leave")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Leave the organisation")
    public void leaveOrg(Authentication authentication, @PathVariable String orgId) {
        User user = (User) authentication.getPrincipal();
        Organisation organisation = organisationService.getOrg(orgId);
        organisationService.leaveOrganisation(organisation, user);
    }

}
