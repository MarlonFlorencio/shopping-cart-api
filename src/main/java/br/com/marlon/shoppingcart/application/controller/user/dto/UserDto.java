package br.com.marlon.shoppingcart.application.controller.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

@Data
@JsonPropertyOrder({ "id", "email", "name" })
public class UserDto extends ResourceSupport {

	@JsonProperty("id")
	private String key;
	private String email;
	private String name;

}
