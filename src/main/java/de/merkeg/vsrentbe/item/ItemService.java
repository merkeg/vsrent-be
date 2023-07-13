package de.merkeg.vsrentbe.item;

import de.merkeg.vsrentbe.exception.ItemNotFoundException;
import de.merkeg.vsrentbe.item.dto.ItemRequestDTO;
import de.merkeg.vsrentbe.org.Organisation;
import de.merkeg.vsrentbe.user.User;
import de.merkeg.vsrentbe.util.Base58;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    public List<Item> listItems(Pageable pageable) {
            return itemRepository.findAll(pageable).getContent();
    }

    public Item createItem(User creator, Organisation organisation, ItemRequestDTO itemRequestDTO) {
        Item item = Item.builder()
                .id(Base58.uuid58())
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

    public void deleteItem(String itemId){
        Item item = getItem(itemId);
        itemRepository.delete(item);
    }

    public Item getItem(String itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isEmpty()) {
            throw new ItemNotFoundException();
        }
        return item.get();
    }
}
