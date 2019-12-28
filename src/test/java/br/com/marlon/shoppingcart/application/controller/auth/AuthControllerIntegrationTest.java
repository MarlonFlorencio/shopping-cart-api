package br.com.marlon.shoppingcart.application.controller.auth;

import br.com.marlon.shoppingcart.application.controller.auth.dto.AuthResult;
import br.com.marlon.shoppingcart.application.controller.auth.dto.SignUpDto;
import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.model.Role;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.testing.RestIntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerIntegrationTest extends RestIntegrationTest {

  private static final String EMAIL = "test@test.com";
  private static final String PASSWORD = "12345";
  private static final String NAME = "Test";

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Test
  public void registrationWorksThroughAllLayers() throws Exception {

    //
    User user = buildUser();
    when(userService.createGeneralUser(EMAIL, PASSWORD, NAME, RoleEnum.USER)).thenReturn(user);
    when(userService.loadUserByUsername(EMAIL)).thenReturn(user);

    SignUpDto signUpDto = new SignUpDto(EMAIL, PASSWORD, NAME );

    //
    MvcResult result = mockMvc.perform(post("/auth/signup")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(signUpDto)))
            .andExpect(status().isOk())
            .andReturn();

    //
    String contentAsString = result.getResponse().getContentAsString();
    AuthResult response = objectMapper.readValue(contentAsString, AuthResult.class);

    assertTrue(isNotBlank(response.getToken()));
  }

  private  User buildUser() {

    User user = new User();
    user.setId("1");
    user.setEmail(EMAIL);
    user.setPassword( passwordEncoder.encode(PASSWORD));
    user.setName(NAME);
    user.setAccountNonExpired(true);
    user.setAccountNonLocked(true);
    user.setCredentialsNonExpired(true);
    user.setEnabled(true);
    user.setRoles(Arrays.asList(new Role("2", "USER")));

    return user;
  }

}
