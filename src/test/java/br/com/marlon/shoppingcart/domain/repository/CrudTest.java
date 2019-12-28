package br.com.marlon.shoppingcart.domain.repository;

import br.com.marlon.shoppingcart.domain.model.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CrudTest {

	@Autowired
	private RoleRepository repository;

	@Test
	public void shouldPerformCRUDOperations() {
		final Role role =  new Role();
		role.setDescription("Teste");

		repository.deleteAll();

		repository.save(role);

		assertThat(repository.findAll())
				.hasSize(1)
				.first()
				.isEqualToComparingFieldByField(role);

		repository.deleteById(role.getId());

		assertThat(repository.count()).isZero();
	}

}

