package br.com.marlon.shoppingcart.application.controller.user;

import br.com.marlon.shoppingcart.application.controller.AbstractUserAuthController;
import br.com.marlon.shoppingcart.application.controller.auth.dto.AuthResult;
import br.com.marlon.shoppingcart.application.controller.user.dto.UserDto;
import br.com.marlon.shoppingcart.application.controller.user.dto.UserUpdateDto;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static br.com.marlon.shoppingcart.application.controller.user.dto.UserConverter.toDto;
import static br.com.marlon.shoppingcart.application.controller.user.dto.UserConverter.toEntity;

@Api(tags = "UserController")
@RestController
@RequestMapping("/api/user")
public class UserController extends AbstractUserAuthController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "Return info from authenticated user" )
	@GetMapping("/my-info")
	public UserDto myInfo(Authentication authentication) {
		User user = service.findById(getPrincipal(authentication).getId());
		return toDto(user);
	}

	@ApiOperation(value = "Find an user by id" )
	@GetMapping("/{id}")
	public UserDto findById(@PathVariable("id") String id, Authentication authentication) {
		validateAccess(id, authentication);
		User user = service.findById(id);
		return toDto(user);
	}

	@ApiOperation(value = "Update the user authenticated")
	@PutMapping
	public AuthResult update(@RequestBody UserUpdateDto user, Authentication authentication) {
		validateAccess(user.getId(), authentication);
		User updatedUser = service.update(toEntity(user));

		String token = createToken(updatedUser);
		return new AuthResult(token);
	}

}