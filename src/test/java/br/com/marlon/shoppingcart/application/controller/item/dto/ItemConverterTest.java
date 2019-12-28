package br.com.marlon.shoppingcart.application.controller.item.dto;

import br.com.marlon.shoppingcart.domain.model.Item;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ItemConverterTest {

    public static final String VALID_ID = "VALID_ID";
    public static final String VALID_NAME = "VALID_NAME";

    @Test
    public void whenEmptyString_thenAccept() {
        ItemDto dto = ItemConverter.toDto(buildEntity());

        assertEquals(buildDto(), dto);
    }

    @Test
    public void whenEmptyString_thenAccept1() {
        Page<Item> itemsPage = new PageImpl(Arrays.asList(buildEntity()));
        Page<ItemDto> itemsDtoPage = ItemConverter.toDtoPage(itemsPage);

        assertEquals(buildDto(), itemsDtoPage.getContent().get(0));
    }

    @Test
    public void whenEmptyString_thenAcc1ept() {
        ItemDto dto = ItemConverter.toDto(new Item());

        assertNull(dto.getKey());
        assertNull(dto.getName());
        assertNull(dto.getPrice());
    }

    @Test(expected = NullPointerException.class)
    public void whenEmptyString_thenAcc1ep1t() {
        ItemConverter.toDto(null);
    }

    @Test
    public void whenEmptyString_thenA1ccept() {
        Item entity = ItemConverter.toEntity(buildDto());

        assertEquals(buildEntity(), entity);
    }

    @Test
    public void whenEmptyString_thenAcc11ept() {
        Item entity = ItemConverter.toEntity(new ItemDto());

        assertNull(entity.getId());
        assertNull(entity.getName());
        assertNull(entity.getPrice());
    }

    @Test(expected = NullPointerException.class)
    public void whenEmptyString_thenAcc1e1p1t() {
        ItemConverter.toEntity(null);
    }

    private Item buildEntity() {
        Item entity = new Item();
        entity.setId(VALID_ID);
        entity.setName(VALID_NAME);
        entity.setPrice(BigDecimal.ONE);
        return entity;
    }

    private ItemDto buildDto() {
        ItemDto dto = new ItemDto();
        dto.setKey(VALID_ID);
        dto.setName(VALID_NAME);
        dto.setPrice(BigDecimal.ONE);
        return dto;
    }

}
