package br.com.marlon.shoppingcart.application.controller.item;

import br.com.marlon.shoppingcart.application.controller.AbstractController;
import br.com.marlon.shoppingcart.application.controller.item.dto.ItemDto;
import br.com.marlon.shoppingcart.domain.model.Item;
import br.com.marlon.shoppingcart.domain.services.ItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static br.com.marlon.shoppingcart.application.controller.item.dto.ItemConverter.toDto;
import static br.com.marlon.shoppingcart.application.controller.item.dto.ItemConverter.toDtoPage;

@Api(tags = "ItemController")
@RestController
@RequestMapping("/api/item")
public class ItemController extends AbstractController {

	@Autowired
	private ItemService service;
	
	@ApiOperation(value = "Find all items" )
	@GetMapping
	public ResponseEntity<?> findAll(
		@RequestParam(value="page", defaultValue = "0") int page,
		@RequestParam(value="pageSize", defaultValue = "25") int pageSize,
		@RequestParam(value="direction", defaultValue = "asc") String direction,
		@RequestParam(value="propertyOrder", defaultValue = "name") String propertyOrder,
		PagedResourcesAssembler<ItemDto> assembler) {

		Pageable pageable = getPageable(page, pageSize, direction, propertyOrder);
		Page<Item> items = service.findAll(pageable);

		return getResponseEntity(toDtoPage(items), assembler);
	}

	@ApiOperation(value = "Find all items by name" )
	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findByName(
		@PathVariable("name") String name,
		@RequestParam(value="page", defaultValue = "0") int page,
		@RequestParam(value="pageSize", defaultValue = "25") int pageSize,
		@RequestParam(value="direction", defaultValue = "asc") String direction,
		@RequestParam(value="propertyOrder", defaultValue = "name") String propertyOrder,
		PagedResourcesAssembler<ItemDto> assembler) {

		Pageable pageable = getPageable(page, pageSize, direction, propertyOrder);
		Page<Item> items = service.findByName(name, pageable);
		return getResponseEntity(toDtoPage(items), assembler);
	}

	@ApiOperation(value = "Find an item by id" )
	@GetMapping("/{id}")
	public ItemDto findById(@PathVariable("id") String id) {
		Item item = service.findById(id);
		return toDto(item);
	}

}