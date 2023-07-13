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
    private final ItemRepository itemRepository;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('item:list')")
    @Operation(summary = "List active Items from all organisations")
    public ResponseEntity<List<ItemDTO>> listItems(@RequestParam(defaultValue = "0", required = false) Integer page,
                                               @RequestParam(defaultValue = "20", required = false) @Max(100) Integer max) {
        return ResponseEntity.ok(itemService.listItems(PageRequest.of(page, max))
                .stream()
                .map(ItemMapper.INSTANCE::mapToDTO)
                .filter(ItemDTO::isEnabled)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{orgId}")
    @PreAuthorize("hasAuthority('item:list')")
    @Operation(summary = "List active Items from specific organisations")
    public ResponseEntity<List<ItemDTO>> listItemsOrg(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                      @RequestParam(defaultValue = "20", required = false) @Max(100) Integer max, @PathVariable String orgId) {
        Organisation organisation = organisationService.getOrg(orgId);
        List<Item> all = itemRepository.findAllByOrganisation(orgId, PageRequest.of(page, max));

        return ResponseEntity.ok(all.stream().map(ItemMapper.INSTANCE::mapToDTO).filter(ItemDTO::isEnabled).collect(Collectors.toList()));
    }


    @SneakyThrows
    @PostMapping("/{orgId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create a new item for organisation")
    public ResponseEntity<ItemDTO> createItem(@PathVariable String orgId, @RequestBody @Valid ItemRequestDTO itemRequestDTO, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Organisation organisation = organisationService.getOrg(orgId);
        OrgRole.throwOnNoOrgPermission(user, organisation, OrgPermission.ITEM_ADD);
        Item item = itemService.createItem(user, organisation, itemRequestDTO);
        return ResponseEntity.created(new URI("/v1/items/"+orgId+"/"+item.getId())).body(ItemMapper.INSTANCE.mapToDTO(item));
    }

    @PutMapping("/{orgId}/{itemId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Update item information")
    public void updateItem(@PathVariable String itemId, @PathVariable String orgId, @RequestBody @Valid ItemRequestDTO itemRequestDTO, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Organisation organisation = organisationService.getOrg(orgId);
        OrgRole.throwOnNoOrgPermission(user, organisation, OrgPermission.ITEM_MODIFY);
        Item item = itemService.getItem(itemId);

    }

    @DeleteMapping("/{orgId}/{itemId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete Item from organisation")
    public ResponseEntity<String> deleteItem(@PathVariable String itemId, @PathVariable String orgId, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Organisation organisation = organisationService.getOrg(orgId);
        OrgRole.throwOnNoOrgPermission(user, organisation, OrgPermission.ITEM_DELETE);

        itemService.deleteItem(itemId);
        return ResponseEntity.ok("");
    }
}
