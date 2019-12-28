package br.com.marlon.shoppingcart.domain.repository;

import br.com.marlon.shoppingcart.domain.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByUserIdAndStatus(String userId, String status);

    Page<Cart> findByUserIdAndStatus(String userId, String status, Pageable pageRequest);
}