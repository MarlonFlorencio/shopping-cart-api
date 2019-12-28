package br.com.marlon.shoppingcart.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Data
@Document(collection = "roles")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

	@Id
	private String id;
	private String description;

	@Override
	public String getAuthority() {
		return this.description;
	}

}
