package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.Item;
import br.com.marlon.shoppingcart.domain.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.marlon.shoppingcart.domain.util.ValidadeUtil.*;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository repository;

	@Transactional
	public Item create(Item item) {
		synchronized (this) {
			item.setId(null);
			validateItem(item, false);
			return repository.save(item);
		}
	}

	@Transactional
	public Item update(Item item) {
		validateItem(item, true);
		synchronized (item.getId()) {
			Item entity = findById(item.getId());
			entity.setName(item.getName());
			entity.setPrice(item.getPrice());
			return repository.save(entity);
		}
	}

	@Transactional
	public void delete(String id) {
		repository.delete(findById(id));
	}

	public Item findById(String id) {
		return repository
				.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No item found for this ID"));
	}

	public Page<Item> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<Item> findByName(String name, Pageable pageable) {
		return repository.findByNameLikeIgnoreCase(name, pageable);
	}

	private void validateItem(Item item, boolean isUpdate) {

		if (isUpdate) {
			validateIsBlank(item.getId(), "Id is required");
		}

		validateIsBlank(item.getName(), "Name is required");
		validateIsNull(item.getPrice(), "Price is required");
		validateIsGreaterThanZero(item.getPrice(), "Price must be greater than zero");
	}

}
