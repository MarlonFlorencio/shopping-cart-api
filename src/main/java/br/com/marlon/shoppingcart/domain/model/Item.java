package br.com.marlon.shoppingcart.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "items")
public class Item {

	@Id
	private String id;
	private String name;
	private BigDecimal price;

}