package de.merkeg.vsrentbe.org;

import de.merkeg.vsrentbe.org.dto.OrgCreationRequestDTO;
import de.merkeg.vsrentbe.org.dto.OrgInfoDTO;
import de.merkeg.vsrentbe.user.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('org:info')")
    public ResponseEntity<OrgInfoDTO> getOrgInfo(@PathVariable String id) {
        return ResponseEntity.ok(organisationService.getOrgInfo(id));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('org:list')")
    public OrgInfoDTO[] getOrgs() {
        return null;
    }

}
