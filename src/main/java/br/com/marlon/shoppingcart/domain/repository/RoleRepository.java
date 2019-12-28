package br.com.marlon.shoppingcart.domain.repository;

import br.com.marlon.shoppingcart.domain.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

	Optional<Role> findByDescription(String description);

}
