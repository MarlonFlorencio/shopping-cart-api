package br.com.marlon.shoppingcart.application.config;

import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.model.Role;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.repository.RoleRepository;
import br.com.marlon.shoppingcart.domain.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class InitMongoDataConfig {

	@Bean
	CommandLineRunner initRoles(RoleRepository roleRepository, UserService userService) {

		return args -> {

			synchronized (this) {
				Optional<Role> adminRole = roleRepository.findByDescription(RoleEnum.ADMIN.name());
				if (!adminRole.isPresent()) {
					Role newAdminRole = new Role();
					newAdminRole.setDescription(RoleEnum.ADMIN.name());
					roleRepository.save(newAdminRole);
				}

				Optional<Role> userRole = roleRepository.findByDescription(RoleEnum.USER.name());
				if (!userRole.isPresent()) {
					Role newUserRole = new Role();
					newUserRole.setDescription(RoleEnum.USER.name());
					roleRepository.save(newUserRole);
				}

				String email = "admin@test.com";
				Optional<User> admin = userService.findByEmail(email);
				if (!admin.isPresent()) {
					userService.createGeneralUser(email, "admin123", "Admin", RoleEnum.ADMIN);
				}
			}
		};
	}
}
