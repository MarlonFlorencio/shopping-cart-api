package br.com.marlon.shoppingcart.application.config;

import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.model.Item;
import br.com.marlon.shoppingcart.domain.model.Role;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.repository.RoleRepository;
import br.com.marlon.shoppingcart.domain.services.ItemService;
import br.com.marlon.shoppingcart.domain.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class InitMongoDataConfig {

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository, UserService userService, ItemService itemService) {

        return args -> {

            synchronized (this) {

                addRole(roleRepository, RoleEnum.ADMIN);
                addRole(roleRepository, RoleEnum.USER);

                addUser(userService, "admin@test.com", "admin123", "Admin", RoleEnum.ADMIN);
                addUser(userService, "user1@test.com", "123456", "User1", RoleEnum.USER);

                addItem(itemService, "Dell Notebook G3", BigDecimal.valueOf(1.99));
                addItem(itemService, "Mouse Gamer Logitech G502", BigDecimal.valueOf(892.99));
                addItem(itemService, "Gamer DT3sports", BigDecimal.valueOf(912.31));
                addItem(itemService, "Notebook Acer Aspire 5", BigDecimal.valueOf(10.50));
                addItem(itemService, "Monitor LG LED 25", BigDecimal.valueOf(22.86));
                addItem(itemService, "AMD Ryzen 5 3600", BigDecimal.valueOf(4245.86));
                addItem(itemService, "SSD Kingston A400", BigDecimal.valueOf(1214.2));
                addItem(itemService, "Console Sony PlayStation 4", BigDecimal.valueOf(32.73));
                addItem(itemService, "Headset Gamer Astro A40 MixAmp", BigDecimal.valueOf(7432.53));
                addItem(itemService, "AirPods", BigDecimal.valueOf(234.24));
                addItem(itemService, "Intel Core i5-9400F Coffee Lake", BigDecimal.valueOf(23.52));
            }

        };

    }

    private void addItem(ItemService itemService, String name, BigDecimal price) {
        Optional<Item> item = itemService.findFirstByName(name);
        if (!item.isPresent()) {
            Item newItem = new Item();
            newItem.setName(name);
            newItem.setPrice(price);
            itemService.create(newItem);
        }
    }

    private void addUser(UserService userService, String email, String password, String name,  RoleEnum role ) {
        Optional<User> admin = userService.findByEmail(email);
        if (!admin.isPresent()) {
            userService.create(email, password, name, role);
        }
    }

    private void addRole(RoleRepository roleRepository, RoleEnum role) {
        Optional<Role> adminRole = roleRepository.findByDescription(role.name());
        if (!adminRole.isPresent()) {
            Role newAdminRole = new Role();
            newAdminRole.setDescription(role.name());
            roleRepository.save(newAdminRole);
        }
    }

}
