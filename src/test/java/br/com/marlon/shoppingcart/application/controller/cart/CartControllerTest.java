package br.com.marlon.shoppingcart.application.controller.cart;

import br.com.marlon.shoppingcart.application.controller.cart.dto.CartDto;
import br.com.marlon.shoppingcart.domain.model.Cart;
import br.com.marlon.shoppingcart.domain.model.CartItem;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.testing.RestIntegrationTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static br.com.marlon.shoppingcart.application.controller.cart.dto.CartConverter.toDto;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CartControllerTest extends RestIntegrationTest {

    @Test
    public void findClosedCarts_shouldReturnPage() throws Exception {

        //
        User user = buildUser();
        Cart cart = buildCart();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        when(cartService.findClosedCarts(any(String.class), any(Pageable.class)))
                .thenReturn(buildPage(cart));

        //
        MvcResult result = mockMvc.perform(get("/api/cart/findClosedCarts")
                .contentType("application/json")
                .header("Authorization", createToken(user)))
                .andExpect(status().isOk())
                .andReturn();

        //
        String contentAsString = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readValue(contentAsString, ObjectNode.class)
                .get("_embedded")
                .get("cartDtoes");

        List<CartDto> cartDtoes = objectMapper.readValue(jsonNode.toString(), new TypeReference<List<CartDto>>() {});
        assertEquals(toDto(cart), cartDtoes.get(0));
    }

    private Page<Cart> buildPage(Cart cart) {
        return new PageImpl<>(Arrays.asList(cart));
    }

    private Cart buildCart() {

        Cart cart = new Cart();
        cart.setId("CART_1");
        cart.setUserId("1");
        cart.setStatus("DRAFT");

        CartItem cartItem = new CartItem();
        cartItem.setItemId("ITEM_1");
        cartItem.setName("Notebook");
        cartItem.setQuantity(4L);
        cartItem.setPrice(BigDecimal.valueOf(15.5));

        HashSet<CartItem> items = new HashSet<>();
        items.add(cartItem);
        cart.setItems(items);

        return cart;
    }

}
