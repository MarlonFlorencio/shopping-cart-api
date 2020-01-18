package br.com.marlon.shoppingcart.domain.repository;

import br.com.marlon.shoppingcart.domain.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, String> {

    Page<Item> findByNameLikeIgnoreCase(String name, Pageable pageRequest);

    Optional<Item> findFirstByNameLikeIgnoreCase(String name);
}