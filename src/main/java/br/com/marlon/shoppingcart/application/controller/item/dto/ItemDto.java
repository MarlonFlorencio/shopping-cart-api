package br.com.marlon.shoppingcart.application.controller.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
@JsonPropertyOrder({ "id", "name", "value" })
public class ItemDto extends ResourceSupport {

	@JsonProperty("id")
	private String key;
	private String name;
	private BigDecimal price;

}
