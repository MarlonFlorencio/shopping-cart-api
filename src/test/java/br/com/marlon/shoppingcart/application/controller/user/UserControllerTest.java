package br.com.marlon.shoppingcart.application.controller.user;

import br.com.marlon.shoppingcart.application.controller.auth.dto.AuthResult;
import br.com.marlon.shoppingcart.application.controller.user.dto.UserDto;
import br.com.marlon.shoppingcart.application.controller.user.dto.UserUpdateDto;
import br.com.marlon.shoppingcart.domain.model.User;
import br.com.marlon.shoppingcart.testing.RestIntegrationTest;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserControllerTest extends RestIntegrationTest {

    @Test
    public void findById_withValidInput_shouldReturnUser() throws Exception {

        //
        User user = buildUser();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        when(userService.findById(ID)).thenReturn(user);

        //
        MvcResult result = mockMvc.perform(get("/api/user/{id}", ID)
                .contentType("application/json")
                .header("Authorization", createToken(user)))
                .andExpect(status().isOk())
                .andReturn();

        //
        String contentAsString = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals(user.getId(), userDto.getKey());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    public void myInfo_withValidInput_shouldReturnUser() throws Exception {

        //
        User user = buildUser();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);
        when(userService.findById(ID)).thenReturn(user);

        //
        MvcResult result = mockMvc.perform(get("/api/user/my-info")
                .contentType("application/json")
                .header("Authorization", createToken(user)))
                .andExpect(status().isOk())
                .andReturn();

        //
        String contentAsString = result.getResponse().getContentAsString();
        UserDto userDto = objectMapper.readValue(contentAsString, UserDto.class);

        assertEquals(user.getId(), userDto.getKey());
        assertEquals(user.getName(), userDto.getName());
        assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    public void findById_withInvalidToken_shouldReturnForbidden() throws Exception {
        //
        User user = buildUser();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);

        //
        mockMvc.perform(get("/api/user/{id}", ID)
                .contentType("application/json")
                .header("Authorization", "INVALID_TOKEN"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    public void update_withValidInput_shouldReturnToken() throws Exception {
        //
        User user = buildUser();
        when(userService.loadUserByUsername(EMAIL)).thenReturn(user);

        when(userService.update(any(User.class))).thenReturn(user);

        UserUpdateDto userUpdateDto = new UserUpdateDto() ;
        userUpdateDto.setId(ID);
        userUpdateDto.setEmail(EMAIL);
        userUpdateDto.setName(NAME);

        //
        MvcResult result = mockMvc.perform(put("/api/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userUpdateDto))
                .header("Authorization", createToken(user)))
                .andExpect(status().isOk())
                .andReturn();

        //
        String contentAsString = result.getResponse().getContentAsString();
        AuthResult authResult = objectMapper.readValue(contentAsString, AuthResult.class);
        assertTrue(isNotBlank(authResult.getToken()));
    }
}
