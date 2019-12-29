package br.com.marlon.shoppingcart.domain.services;

import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import br.com.marlon.shoppingcart.domain.exception.ResourceNotFoundException;
import br.com.marlon.shoppingcart.domain.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.Predicate;

import static br.com.marlon.shoppingcart.domain.enums.RoleEnum.USER;
import static org.junit.Assert.*;

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
	public void create_allValidParams_shouldCreateUser() {
		String email = "0t0g02s026ts@test.com";
		User user = service.create(email , PASSWORD, NAME, USER);

		assertNotNull(user.getId());
		assertNotNull(user.getPassword());
		assertEquals(email, user.getEmail());
		assertEquals(NAME, user.getName());
		assertTrue(user.getAccountNonExpired());
		assertTrue(user.isAccountNonExpired());
		assertTrue(user.getAccountNonLocked());
		assertTrue(user.isAccountNonLocked());
		assertTrue(user.getCredentialsNonExpired());
		assertTrue(user.isCredentialsNonExpired());
		assertTrue(user.getEnabled());
		assertTrue(user.isEnabled());

		Predicate<GrantedAuthority> hasOnlyUserRole = r -> r.getAuthority().equals(USER.name());
		assertTrue(user.getRoles().stream().allMatch(hasOnlyUserRole));
		assertTrue(user.getAuthorities().stream().allMatch(hasOnlyUserRole));
	}

	@Test(expected = BadRequestException.class)
	public void create_withEmptyEmail_shouldThrowBadRequestException() {
		service.create("", PASSWORD, NAME, USER);
	}

	@Test(expected = BadRequestException.class)
	public void create_withEmptyPassword_shouldThrowBadRequestException() {
		service.create(EMAIL, "", NAME, USER);
	}

	@Test(expected = BadRequestException.class)
	public void create_withEmptyName_shouldThrowBadRequestException() {
		service.create(EMAIL, PASSWORD, "", USER);
	}

	@Test(expected = BadRequestException.class)
	public void create_withNullRole_shouldThrowBadRequestException() {
		service.create(EMAIL, PASSWORD, NAME, null);
	}

	@Test(expected = BadRequestException.class)
	public void create_withEmailAlreadyUsed_shouldThrowBadRequestException() {
		service.create(EMAIL, PASSWORD, NAME, USER);
		service.create(EMAIL, PASSWORD, NAME, USER);
	}

	@Test
	public void findById_withValidId_shouldReturnAUser() {
		String email = "djih384yfh3@test.com";
		User newUser = service.create(email, PASSWORD, NAME, USER);
		User user = service.findById(newUser.getId());
		assertEquals(email, user.getEmail());
	}

	@Test(expected = ResourceNotFoundException.class)
	public void findById_withInValidId_shouldThrowResourceNotFoundException() {
		service.findById("ygr40640f6t40f6g");
	}

	@Test
	public void loadUserByUsername_withValidEmail_shouldReturnAUser() {
		String email = "y7yd3p3h3he@test.com";
		User newUser = service.create(email, PASSWORD, NAME, USER);
		User user = service.loadUserByUsername(newUser.getEmail());

		assertEquals(email, user.getEmail());
	}

	@Test(expected = UsernameNotFoundException.class)
	public void loadUserByUsername_withValidEmail_shouldThrowUsernameNotFoundException() {
		service.loadUserByUsername("321sdyfg67g@test.com");
	}

	@Test
	public void update_withValidUser_shouldUpdateUser() {
		String email = "0at0g12fder02s0s@test.com";
		User newUser = service.create(email , PASSWORD, NAME, USER);

		User user = new User();
		user.setId(newUser.getId());
		user.setEmail(email);
		user.setName(NAME);
		User updatedUser = service.update(user);

		assertEquals(newUser, updatedUser);
		assertEquals(newUser.hashCode(), updatedUser.hashCode());
		assertEquals(newUser.toString(), updatedUser.toString());
		assertEquals(newUser.getId(), updatedUser.getId());
		assertEquals(newUser.getEmail(), updatedUser.getEmail());
		assertEquals(newUser.getName(), updatedUser.getName());
	}

	@Test
	public void update_withNewEmailAndPassword_shouldUpdateUser() {
		String email = "20h0g2jff02s@test.com";
		User newUser = service.create(email , PASSWORD, NAME, USER);

		User user = new User();
		user.setId(newUser.getId());
		user.setEmail("9yvbdyb345342k@test.com");
		user.setPassword("98765");
		user.setName(NAME);
		User updatedUser = service.update(user);

		assertEquals(newUser.getId(), updatedUser.getId());
		assertEquals(newUser.getName(), updatedUser.getName());
		assertNotEquals(newUser.getEmail(), updatedUser.getEmail());
		assertNotEquals(newUser.getPassword(), updatedUser.getPassword());
	}

	@Test(expected = BadRequestException.class)
	public void update_withNullId_shouldThrowBadRequestException() {
		User user = new User();
		user.setId(null);
		user.setEmail(EMAIL);
		user.setName(NAME);
		service.update(user);
	}

	@Test(expected = BadRequestException.class)
	public void update_withNullEmail_shouldThrowBadRequestException() {
		User user = new User();
		user.setId("1");
		user.setEmail(null);
		user.setName(NAME);
		service.update(user);
	}

	@Test(expected = BadRequestException.class)
	public void update_withNullName_shouldThrowBadRequestException() {
		User user = new User();
		user.setId("1");
		user.setEmail(EMAIL);
		user.setName(null);
		service.update(user);
	}

}

