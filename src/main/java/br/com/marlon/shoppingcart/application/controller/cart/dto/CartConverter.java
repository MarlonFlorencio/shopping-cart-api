package br.com.marlon.shoppingcart.application.controller.cart.dto;

import br.com.marlon.shoppingcart.application.controller.cart.CartController;
import br.com.marlon.shoppingcart.domain.model.Cart;
import br.com.marlon.shoppingcart.domain.model.CartItem;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CartConverter {

    public static CartDto toDto(Cart entity) {
        CartDto dto = new CartDto();

        dto.setKey(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setStatus(entity.getStatus());
        dto.setDate(entity.getDate());
        dto.setItems(toDtoList(entity.getItems()));
        dto.setTotal(getCartTotal(dto.getItems()));

        dto.add(linkTo(methodOn(CartController.class).findById(entity.getId(), null)).withSelfRel());

        return dto;
    }

    private static CartItemDto toDto(CartItem entity) {
        CartItemDto dto = new CartItemDto();

        dto.setItemId(entity.getItemId());
        dto.setName(entity.getName());
        dto.setQuantity(entity.getQuantity());
        dto.setPrice(entity.getPrice());
        dto.setTotal(entity.getTotal());

        return dto;
    }

    private static BigDecimal getCartTotal(List<CartItemDto> list) {
        return list.stream()
                .map( c -> c.getTotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static List<CartItemDto> toDtoList(Set<CartItem> list) {

        if (list == null)  {
            return Collections.emptyList();
        }

        return list
                .stream()
                .map(CartConverter::toDto)
                .collect(Collectors.toList());
    }

    public static Page<CartDto> toDtoPage(Page<Cart> page) {
        return page.map(CartConverter::toDto);
    }

}
