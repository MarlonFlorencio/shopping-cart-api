package br.com.marlon.shoppingcart.application.controller.item.dto;

import br.com.marlon.shoppingcart.application.controller.item.ItemController;
import br.com.marlon.shoppingcart.domain.model.Item;
import org.springframework.data.domain.Page;

import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ItemConverter {

    public static Item toEntity(ItemDto itemDto) {

        requireNonNull(itemDto, "itemDto must not be null");

        Item item = new Item();
        item.setId(itemDto.getKey());
        item.setName(itemDto.getName());
        item.setPrice(itemDto.getPrice());
        return item;
    }

    public static ItemDto toDto(Item item) {

        requireNonNull(item, "Item must not be null");

        ItemDto itemDto = new ItemDto();
        itemDto.setKey(item.getId());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPrice());

        itemDto.add(linkTo(methodOn(ItemController.class).findById(item.getId())).withSelfRel());

        return itemDto;
    }

    public static Page<ItemDto> toDtoPage(Page<Item> page) {
        return page.map(ItemConverter::toDto);
    }

}
