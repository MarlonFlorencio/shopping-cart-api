package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static br.com.marlon.shoppingcart.domain.enums.RoleEnum.USER;

@DataMongoTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

	private static final String EMAIL = "test@test.com";
	private static final String PASSWORD = "12345";
	private static final String NAME = "Test";

	@Autowired
	private UserService service;

	@Test
	public void shouldPerformCRUDOperations() {
		User user = service.createGeneralUser("0t0g02s026ts@test.com" , PASSWORD, NAME, USER);
		Assert.assertEquals("Test", user.getName());
	}

	@Test(expected = BadRequestException.class)
	public void shouldPerformCRUDOperation1s() {
		service.createGeneralUser("", PASSWORD, NAME, USER);
	}

	@Test(expected = BadRequestException.class)
	public void shouldPerformCRUDOperatio1n1s() {
		service.createGeneralUser(EMAIL, null, NAME, USER);
	}

	@Test(expected = BadRequestException.class)
	public void shouldPerformCRUDOperatio1n1s1() {
		service.createGeneralUser(EMAIL, PASSWORD, null, USER);
	}

	@Test
	public void shouldPerformCRUDOperatio111ns() {
		User newUser = service.createGeneralUser("djih384yfh3@test.com", PASSWORD, NAME, USER);
		User user = service.findById(newUser.getId());

		Assert.assertEquals(newUser.getEmail(), user.getEmail());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void shouldPerformCRUDOpe2222ratddio112221ns() {
		service.findById("ygr40640f6t40f6g");
	}

	@Test
	public void shouldPerformCRUDOperatio112221ns() {
		User newUser = service.createGeneralUser("y7yd3p3h3he@test.com", PASSWORD, NAME, USER);
		User user = service.loadUserByUsername(newUser.getEmail());

		Assert.assertEquals(newUser.getEmail(), user.getEmail());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void shouldPerformCRUDOpe2222ratio112221ns() {
		service.loadUserByUsername("321sdyfg67g@test.com");
	}

}

