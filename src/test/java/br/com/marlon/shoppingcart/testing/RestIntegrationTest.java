package br.com.marlon.shoppingcart.testing;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest
@RunWith(SpringRunner.class)
abstract public class RestIntegrationTest {

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

}
