package br.com.marlon.shoppingcart.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CartItem {

	private String itemId;
	private String name;
	private Long quantity;
	private BigDecimal price;

	public BigDecimal getTotal() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}

	public CartItem(Item item) {
		this.itemId = item.getId();
		this.name = item.getName();
		this.price = item.getPrice();
		this.quantity = 0l;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CartItem cartItem = (CartItem) o;
		return Objects.equals(itemId, cartItem.itemId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemId);
	}
}