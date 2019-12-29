package br.com.marlon.shoppingcart.testing;

import br.com.marlon.shoppingcart.application.security.JwtTokenProvider;
import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.model.Role;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.domain.repository.CartRepository;
import br.com.marlon.shoppingcart.domain.repository.ItemRepository;
import br.com.marlon.shoppingcart.domain.repository.RoleRepository;
import br.com.marlon.shoppingcart.domain.repository.UserRepository;
import br.com.marlon.shoppingcart.domain.services.CartService;
import br.com.marlon.shoppingcart.domain.services.ItemService;
import br.com.marlon.shoppingcart.domain.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@WebMvcTest
@RunWith(SpringRunner.class)
abstract public class RestIntegrationTest {

    protected static final String ID = "1";
    protected static final String EMAIL = "test@test.com";
    protected static final String PASSWORD = "12345";
    protected static final String NAME = "Test";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected UserService userService;

    @MockBean
    protected ItemService itemService;

    @MockBean
    protected CartService cartService;

    @MockBean
    protected UserRepository userRepository;

    @MockBean
    protected ItemRepository userwRepository;

    @MockBean
    protected RoleRepository roleRepository;

    @MockBean
    protected CartRepository cartRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    protected String createToken(User user) {

        List<String> roles = user
                .getRoles()
                .stream()
                .map(Role::getDescription)
                .collect(Collectors.toList());

        return "Bearer " + tokenProvider.createToken(user.getUsername(), roles);
    }

    protected User buildUser() {

        User user = new User();
        user.setId(ID);
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setName(NAME);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setRoles(Arrays.asList(new Role("2", RoleEnum.USER.name())));

        return user;
    }

}
