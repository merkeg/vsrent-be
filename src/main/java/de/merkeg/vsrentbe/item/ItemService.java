package de.merkeg.vsrentbe.item;

import de.merkeg.vsrentbe.item.dto.ItemRequestDTO;
import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    public List<Item> listItems(Pageable pageable) {
            return itemRepository.findAll(pageable).getContent();
    }

    public Item createItem(User creator, Organisation organisation, ItemRequestDTO itemRequestDTO) {
        Item item = Item.builder()
                .creator(creator)
                .enabled(true)
                .organisation(organisation)
                .description(itemRequestDTO.getDescription())
                .imageHandle(itemRequestDTO.getImageHandle())
                .name(itemRequestDTO.getName())
                .quantity(itemRequestDTO.getQuantity())
                .build();
        itemRepository.save(item);
        return item;
    }
}
