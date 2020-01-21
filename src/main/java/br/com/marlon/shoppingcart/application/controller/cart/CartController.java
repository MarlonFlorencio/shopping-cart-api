package br.com.marlon.shoppingcart.application.controller.cart;

import br.com.marlon.shoppingcart.application.controller.AbstractUserAuthController;
import br.com.marlon.shoppingcart.application.controller.cart.dto.CartDto;
import br.com.marlon.shoppingcart.domain.model.Cart;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.services.CartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static br.com.marlon.shoppingcart.application.controller.cart.dto.CartConverter.toDto;
import static br.com.marlon.shoppingcart.application.controller.cart.dto.CartConverter.toDtoPage;

@Api(tags = "CartController")
@RestController
@RequestMapping("/api/cart")
public class CartController extends AbstractUserAuthController {

	@Autowired
	private CartService service;
	
	@ApiOperation(value = "Add item to the draft Cart")
	@PostMapping("/add-item/{itemId}")
	public CartDto addItem(
			@PathVariable("itemId") String itemId,
			Authentication authentication) {

		User user = getPrincipal(authentication);
		Cart cart = service.addItem(user.getId(), itemId);

		return toDto(cart);
	}

	@ApiOperation(value = "Remove item from the draft Cart")
	@PostMapping("/remove-item/{itemId}")
	public CartDto removeItem(
		@PathVariable("itemId") String itemId,
		Authentication authentication) {

		User user = getPrincipal(authentication);
		Cart cart = service.removeItem(user.getId(), itemId);

		return toDto(cart);
	}

	@ApiOperation(value = "Remove one item from the draft Cart")
	@PostMapping("/remove-one-item/{itemId}")
	public CartDto removeOneItem(
		@PathVariable("itemId") String itemId,
		Authentication authentication) {

		User user = getPrincipal(authentication);
		Cart cart = service.removeOneItem(user.getId(), itemId);

		return toDto(cart);
	}

	@ApiOperation(value = "Close the draft Cart" )
	@PostMapping("/close")
	public CartDto closeCart(Authentication authentication) {

		User user = getPrincipal(authentication);
		Cart cart = service.closeCart(user.getId());

		return toDto(cart);
	}

	@ApiOperation(value = "Find cart by id" )
	@GetMapping("/{id}")
	public CartDto findById(
			@PathVariable("id") String id,
			Authentication authentication) {

		Cart cart = service.findById(id);
		validateAccess(cart.getUserId(), authentication);

		return toDto(cart);
	}

	@ApiOperation(value = "Find the drafted cart" )
	@GetMapping("/draftedCart")
	public CartDto getDraftedCart(Authentication authentication) {
		User user = getPrincipal(authentication);
		Cart cart = service.getDraftedCart(user.getId()).orElse(new Cart());
		return toDto(cart);
	}

	@ApiOperation(value = "Find all closed carts" )
	@GetMapping("/closedCarts")
	public ResponseEntity<?> getClosedCarts(
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="pageSize", defaultValue = "25") int pageSize,
			@RequestParam(value="direction", defaultValue = "asc") String direction,
			@RequestParam(value="propertyOrder", defaultValue = "id") String propertyOrder,
			Authentication authentication,
			PagedResourcesAssembler assembler) {

		User user = getPrincipal(authentication);

		Pageable pageable = getPageable(page, pageSize, direction, propertyOrder);
		Page<Cart> items = service.findClosedCarts(user.getId(), pageable);
		return getResponseEntity(toDtoPage(items), assembler);
	}

}