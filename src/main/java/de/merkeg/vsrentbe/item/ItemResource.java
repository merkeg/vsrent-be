package de.merkeg.vsrentbe.item;

import de.merkeg.vsrentbe.item.dto.ItemDTO;
import de.merkeg.vsrentbe.item.dto.ItemMapper;
import de.merkeg.vsrentbe.item.dto.ItemRequestDTO;
import de.merkeg.vsrentbe.membership.OrgPermission;
import de.merkeg.vsrentbe.membership.OrgRole;
import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.org.OrganisationService;
import de.merkeg.vsrentbe.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Items")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/items")
@Validated
public class ItemResource {

    private final ItemService itemService;
    private final OrganisationService organisationService;

    @GetMapping("/")
    @Operation
    @PreAuthorize("hasAuthority('item:list')")
    public ResponseEntity<List<ItemDTO>> getItems(@RequestParam(defaultValue = "0", required = false) Integer page,
                                               @RequestParam(defaultValue = "20", required = false) @Max(100) Integer max) {
        return ResponseEntity.ok(itemService.listItems(PageRequest.of(page, max))
                .stream()
                .map(ItemMapper.INSTANCE::mapToDTO)
                .collect(Collectors.toList()));
    }
    @SneakyThrows
    @PostMapping("/{orgId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ItemDTO> createItem(@PathVariable String orgId, @RequestBody @Valid ItemRequestDTO itemRequestDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Organisation organisation = organisationService.getOrg(orgId);
        OrgRole.throwOnNoOrgPermission(user, organisation, OrgPermission.INVENTORY_ADD);
        Item item = itemService.createItem(user, organisation, itemRequestDTO);
        return ResponseEntity.created(new URI("/v1/items/"+orgId+"/"+item.getId())).body(ItemMapper.INSTANCE.mapToDTO(item));
    }

    @PutMapping("/{orgId}/{itemId}")
    @PreAuthorize("isAuthenticated()")
    public void updateItem(@PathVariable String itemId, @PathVariable String orgId) {

    }
}
