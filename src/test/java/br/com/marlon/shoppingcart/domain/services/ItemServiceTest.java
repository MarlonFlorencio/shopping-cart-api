package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ItemServiceTest {

	@Autowired
	private ItemService service;

	@Test
	public void create_allValidParams_shouldCreateItem() {

		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ONE);

		Item newItem = service.create(item);

		assertNotNull(newItem.getId());
		assertEquals(item.getName(), newItem.getName());
		assertEquals(item.getPrice(), newItem.getPrice());
	}

	@Test(expected = BadRequestException.class)
	public void create_withEmptyName_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("");
		item.setPrice(BigDecimal.ONE);

		service.create(item);
	}
	@Test(expected = BadRequestException.class)
	public void create_withNullPrice_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(null);

		service.create(item);
	}

	@Test(expected = BadRequestException.class)
	public void create_withZeroPrice_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ZERO);

		service.create(item);
	}

	@Test(expected = BadRequestException.class)
	public void create_withNegativePrice_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.valueOf(-1.5));

		service.create(item);
	}

	@Test
	public void findById_withValidId_shouldReturnItem() {

		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ONE);

		Item newItem = service.create(item);

		Item foundId = service.findById(newItem.getId());
		assertEquals(newItem, foundId);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void findById_withInValidId_shouldThrowResourceNotFoundException() {
		service.findById("ygr40640f6t40f6g");
	}

	@Test
	public void update_withValidUser_shouldUpdateItem() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ONE);

		Item newItem = service.create(item);
		newItem.setName("Notebook 2");
		newItem.setPrice(BigDecimal.TEN);

		Item updatedItem = service.update(newItem);

		assertEquals(newItem, updatedItem);
		assertEquals(newItem.toString(), updatedItem.toString());
		assertEquals(newItem.hashCode(), updatedItem.hashCode());
		assertEquals(newItem.getId(), updatedItem.getId());
		assertEquals(newItem.getId(), updatedItem.getId());
		assertEquals(newItem.getName(), updatedItem.getName());
		assertEquals(newItem.getPrice(), updatedItem.getPrice());
	}

	@Test(expected = BadRequestException.class)
	public void update_withEmptyName_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("");
		item.setPrice(BigDecimal.ONE);

		service.create(item);
	}

	@Test(expected = BadRequestException.class)
	public void update_withNullId_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setId(null);
		item.setName("Notebook");
		item.setPrice(BigDecimal.ONE);

		service.update(item);
	}

	@Test(expected = BadRequestException.class)
	public void update_withNullPrice_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(null);

		service.update(item);
	}

	@Test(expected = BadRequestException.class)
	public void update_withZeroPrice_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ZERO);

		service.update(item);
	}

	@Test(expected = BadRequestException.class)
	public void update_withNegativePrice_shouldThrowBadRequestException() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.valueOf(-1.5));

		service.update(item);
	}

	@Test
	public void delete_withValidUser_shouldUpdateItem() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ONE);

		Item newItem = service.create(item);

		service.delete(newItem.getId());

		try {
			service.findById(newItem.getId());
		} catch (ResourceNotFoundException ex) {
			assertEquals("No item found for this ID", ex.getMessage());
		}
	}

	@Test
	public void findAll_withValidRecord_shouldFilledPage() {
		Item item = new Item();
		item.setName("Notebook");
		item.setPrice(BigDecimal.ONE);
		service.create(item);

		Page<Item> page = service.findAll(Pageable.unpaged());

		assertFalse(page.getContent().isEmpty());
		assertTrue(page.getTotalElements() > 0);
	}

	@Test
	public void findByName_withValidRecords_shouldFilledPage() {
		Item item1 = new Item();
		item1.setName("Keyboard gamer");
		item1.setPrice(BigDecimal.ONE);
		service.create(item1);

		Item item2 = new Item();
		item2.setName("Monitor");
		item2.setPrice(BigDecimal.ONE);
		service.create(item2);

		Page<Item> page = service.findByName("yboard game", Pageable.unpaged());

		assertEquals(1, page.getTotalElements());
		assertEquals(item1, page.getContent().get(0));
	}

}

