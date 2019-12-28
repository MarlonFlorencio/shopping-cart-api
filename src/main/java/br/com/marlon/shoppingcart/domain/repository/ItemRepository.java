package br.com.marlon.shoppingcart.domain.repository;

import br.com.marlon.shoppingcart.domain.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {

    Page<Item> findByNameLikeIgnoreCase(String name, Pageable pageRequest);
}