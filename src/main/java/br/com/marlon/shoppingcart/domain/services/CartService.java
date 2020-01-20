package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.Cart;
import br.com.marlon.shoppingcart.domain.model.CartItem;
import br.com.marlon.shoppingcart.domain.model.Item;
import br.com.marlon.shoppingcart.domain.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Service
public class CartService {

	private static final String STATUS_CLOSED = "CLOSED";
	private static final String STATUS_DRAFT = "DRAFT";

	@Autowired
	private CartRepository repository;

	@Autowired
	private ItemService itemService;

	@Transactional
	public Cart addItem(String userId, String itemId) {

		Item item = itemService.findById(itemId);

		synchronized (userId) {

			Cart cart = getOrCreateDraftCart(userId);

			Set<CartItem> list = cart.getItems().stream().collect(Collectors.toSet());

			CartItem cartItem = list
					.stream()
					.filter( c -> c.getItemId().equals(item.getId()))
					.findFirst()
					.orElse( new CartItem(item));

			AtomicLong quantity = new AtomicLong(cartItem.getQuantity());
			cartItem.setQuantity(quantity.incrementAndGet());
			list.add(cartItem);
			cart.setItems(list);

			return repository.save(cart);
		}
	}

	@Transactional
	public Cart removeItem(String userId, String itemId) {

		Item item = itemService.findById(itemId);

		synchronized (userId) {

			Cart cart = getOrCreateDraftCart(userId);

			Set<CartItem> list =
				cart.getItems()
					.stream()
					.filter( c -> !item.getId().equals(c.getItemId()) )
					.collect(Collectors.toSet());

			cart.setItems(list);

			return repository.save(cart);
		}
	}

	private Cart getOrCreateDraftCart(String userId) {
		Optional<Cart> optionalCart = repository.findByUserIdAndStatus(userId, STATUS_DRAFT);
		return optionalCart.orElseGet( () -> createCart(userId) );
	}

	private Cart createCart(String userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setStatus(STATUS_DRAFT);
		cart.setItems(Collections.emptySet());
		return repository.save(cart);
	}

	@Transactional
	public Cart closeCart(String userId) {

		Cart cart = getDraftedCart(userId)
				.orElseThrow(() -> new ResourceNotFoundException("No draft Cart was found"));

		cart.setStatus(STATUS_CLOSED);
		cart.setDate(LocalDateTime.now());

		return repository.save(cart);
	}

	public Optional<Cart> getDraftedCart(String userId) {
		return repository.findByUserIdAndStatus(userId, STATUS_DRAFT);
	}

	public Cart findById(String id) {
		return repository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No Cart found for this ID"));
	}

	public Page<Cart> findClosedCarts(String userId, Pageable pageable) {
		return repository.findByUserIdAndStatus(userId, STATUS_CLOSED, pageable);
	}

}
