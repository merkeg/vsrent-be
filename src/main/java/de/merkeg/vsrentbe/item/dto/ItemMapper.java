package de.merkeg.vsrentbe.item.dto;

import de.merkeg.vsrentbe.item.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    public static ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "organisation", expression = "java(item.getOrganisation().getId())")
    @Mapping(target = "creator", expression = "java(item.getCreator().getId())")
    ItemDTO mapToDTO(Item item);
}
