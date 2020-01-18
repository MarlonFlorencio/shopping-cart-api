package br.com.marlon.shoppingcart.application.controller.auth;

import br.com.marlon.shoppingcart.application.controller.AbstractUserAuthController;
import br.com.marlon.shoppingcart.application.controller.auth.dto.AuthResult;
import br.com.marlon.shoppingcart.application.controller.auth.dto.SignInDto;
import br.com.marlon.shoppingcart.application.controller.auth.dto.SignUpDto;
import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "AuthenticationEndpoint") 
@RestController
@RequestMapping("/auth")
public class AuthController extends AbstractUserAuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@ApiOperation(value = "Authenticate an user and returns a token")
	@PostMapping("/signin")
	public AuthResult signin(@RequestBody SignInDto signInDto) {

		String email = signInDto.getEmail();
		String password = signInDto.getPassword();

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		User user = userService.loadUserByUsername(email);
		String token = createToken(user);
		return new AuthResult(token);
	}

	@ApiOperation(value = "Register an user and returns a token")
	@PostMapping("/registration")
	public AuthResult registration(@RequestBody SignUpDto signUpDto) {

		String email = signUpDto.getEmail();
		String password = signUpDto.getPassword();
		String name = signUpDto.getName();

		User user = userService.create(email, password, name, RoleEnum.USER);
		String token = createToken(user);

		return new AuthResult(token);
	}
}
