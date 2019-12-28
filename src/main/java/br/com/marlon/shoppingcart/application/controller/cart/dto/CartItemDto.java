package br.com.marlon.shoppingcart.application.controller.cart.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {

	private String itemId;
	private String name;
	private Long quantity;
	private BigDecimal price;
	private BigDecimal total;

}