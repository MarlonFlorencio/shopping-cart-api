package br.com.marlon.shoppingcart.application.controller.auth.dto;

import lombok.Data;

@Data
public class SignInDto {
	private String email;
	private String password;
}
