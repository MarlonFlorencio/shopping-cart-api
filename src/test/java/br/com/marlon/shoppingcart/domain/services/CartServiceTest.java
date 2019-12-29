package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.model.Cart;
import br.com.marlon.shoppingcart.domain.model.CartItem;
import br.com.marlon.shoppingcart.domain.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CartServiceTest {

    @Autowired
    private CartService service;

    @Autowired
    private ItemService itemService;

    @Test
    public void create_allValidParams_shouldCreatecart() {

        Item newItem = new Item();
        newItem.setName("Notebook");
        newItem.setPrice(BigDecimal.TEN);

        Item item = itemService.create(newItem);
        String userId = "1";

        Cart cart = service.addItem(userId, item.getId());

        assertNotNull(cart.getId());
        assertEquals(userId, cart.getUserId());
        assertEquals("DRAFT", cart.getStatus());
        assertEquals(1, cart.getItems().size());

        CartItem cartItemDto = cart.getItems().stream().findFirst().get();
        assertEquals(BigDecimal.TEN, cartItemDto.getTotal());
    }

    @Test
    public void findById_allValidParams_shouldReturnCart() {

        Item newItem = new Item();
        newItem.setName("Notebook");
        newItem.setPrice(BigDecimal.TEN);

        Item item = itemService.create(newItem);
        String userId = "2";

        Cart newCart = service.addItem(userId, item.getId());

        Cart cart = service.findById(newCart.getId());
        assertEquals(newCart, cart);
        assertEquals(newCart.toString(), cart.toString());
        assertEquals(newCart.hashCode(), cart.hashCode());

    }
}
