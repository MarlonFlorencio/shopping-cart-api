package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.Item;
import br.com.marlon.shoppingcart.domain.repository.ItemRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ItemService {
	
	@Autowired
	ItemRepository repository;

	@Transactional
	public Item create(Item item) {

		item.setId(null);
		validateItem(item, false);

		Item newItem = repository.save(item);
		return newItem;
	}

	@Transactional
	public Item update(Item item) {

		validateItem(item, true);
		Item entity = findById(item.getId());
		entity.setName(item.getName());
		entity.setPrice(item.getPrice());

		return repository.save(entity);
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

	private void validateItem(Item item, boolean validadeId) {

		if(validadeId && StringUtils.isBlank(item.getId())) {
			throw new BadRequestException("[id] is required");
		}

		if(StringUtils.isBlank(item.getName())) {
			throw new BadRequestException("[name] is required");
		}

		if(item.getPrice() == null) {
			throw new BadRequestException("[value] is required");
		} else {
			if (item.getPrice().equals(BigDecimal.ZERO)) {
				throw new BadRequestException("[value] must be greater than zero");
			}
		}
	}

}
