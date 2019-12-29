package br.com.marlon.shoppingcart.application.controller.auth;

import br.com.marlon.shoppingcart.application.controller.auth.dto.AuthResult;
import br.com.marlon.shoppingcart.application.controller.auth.dto.SignInDto;
import br.com.marlon.shoppingcart.application.controller.auth.dto.SignUpDto;
import br.com.marlon.shoppingcart.domain.enums.RoleEnum;
import br.com.marlon.shoppingcart.domain.exception.BadRequestException;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.testing.RestIntegrationTest;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthControllerTest extends RestIntegrationTest {

    @Test
    public void signup_withValidInput_shouldReturnToken() throws Exception {

        //
        User user = buildUser();
        when(userService.create(EMAIL, PASSWORD, NAME, RoleEnum.USER)).thenReturn(user);
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        SignUpDto signUpDto = new SignUpDto(EMAIL, PASSWORD, NAME);

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

    @Test
    public void signup_withoutEmail_shouldReturnBadRequest() throws Exception {
        //
        when(userService.create("", PASSWORD, NAME, RoleEnum.USER)).thenThrow(BadRequestException.class);
        SignUpDto signUpDto = new SignUpDto("", PASSWORD, NAME);

        //
        MvcResult result = mockMvc.perform(post("/auth/signup")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signUpDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void signin_withValidInput_shouldReturnToken() throws Exception {
        //
        User user = buildUser();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        SignInDto signInDto = new SignInDto(EMAIL, PASSWORD);

        //
        MvcResult result = mockMvc.perform(post("/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signInDto)))
                .andExpect(status().isOk())
                .andReturn();

        //
        String contentAsString = result.getResponse().getContentAsString();
        AuthResult response = objectMapper.readValue(contentAsString, AuthResult.class);
        assertTrue(isNotBlank(response.getToken()));
    }

}
