package br.com.marlon.shoppingcart.application.controller.cart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonPropertyOrder({ "id", "userId", "status", "total", "items" })
public class CartDto extends ResourceSupport {

	@JsonProperty("id")
	private String key;
	private String userId;
	private String status;
	private BigDecimal total;
	private List<CartItemDto> items;

}