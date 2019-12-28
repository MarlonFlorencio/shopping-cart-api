package br.com.marlon.shoppingcart.application.controller.item;

import br.com.marlon.shoppingcart.application.controller.item.dto.ItemDto;
import br.com.marlon.shoppingcart.domain.model.Item;
import br.com.marlon.shoppingcart.domain.services.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.marlon.shoppingcart.application.controller.item.dto.ItemConverter.toDto;
import static br.com.marlon.shoppingcart.application.controller.item.dto.ItemConverter.toEntity;

@Api(tags = "ItemAdminController")
@RestController
@RequestMapping("/api/admin/item")
public class ItemAdminController {
	
	@Autowired
	private ItemService service;

	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new item") 
	@PostMapping
	public ItemDto create(@RequestBody ItemDto item) {
		Item newItem = service.create(toEntity(item));
		return toDto(newItem);
	}
	
	@ApiOperation(value = "Update item")
	@PutMapping
	public ItemDto update(@RequestBody ItemDto item) {
		Item updatedItem = service.update(toEntity(item));
		return toDto(updatedItem);
	}
	
	@ApiOperation(value = "Delete item by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}