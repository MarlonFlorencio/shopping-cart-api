package br.com.marlon.shoppingcart.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document(collection = "carts")
public class Cart {

	@Id
	private String id;
	private String userId;
	private String status;
	private Set<CartItem> items;

}