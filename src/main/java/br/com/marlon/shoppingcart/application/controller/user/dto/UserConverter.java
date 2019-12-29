package br.com.marlon.shoppingcart.application.controller.user.dto;

import br.com.marlon.shoppingcart.application.controller.user.UserController;
import br.com.marlon.shoppingcart.domain.model.User;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserConverter {

    public static User toEntity(UserUpdateDto dto) {
        User entity = new User();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setKey(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());

        dto.add(linkTo(methodOn(UserController.class).findById(user.getId(), null)).withSelfRel());

        return dto;
    }

}
