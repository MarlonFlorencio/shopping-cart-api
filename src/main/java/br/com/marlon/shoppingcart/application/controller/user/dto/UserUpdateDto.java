package br.com.marlon.shoppingcart.application.controller.user.dto;

import lombok.Data;

@Data
public class UserUpdateDto {
	private String id;
	private String email;
	private String name;
	private String password;
}
