package com.example.demo.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.dtos.CartItemsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CartItems {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long price;
	private Long quantity;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Product product;


	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;
	
	public CartItemsDto getCartItemsDto() {
	    CartItemsDto cartItemsDto = new CartItemsDto();
	    cartItemsDto.setId(this.getId());
	    cartItemsDto.setPrice(this.getPrice());
	    cartItemsDto.setQuantity(this.getQuantity());

	    if (this.getProduct() != null) {
	        cartItemsDto.setProductId(this.getProduct().getId());
	        cartItemsDto.setProductName(this.getProduct().getName());
	        cartItemsDto.setImageData(this.getProduct().getImageData());
	    }

	    if (this.getUser() != null) {
	        cartItemsDto.setUserId(this.getUser().getId());
	    }

	    if (this.getOrder() != null) {
	        cartItemsDto.setOrderId(this.getOrder().getId());
	    }

	    return cartItemsDto;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "CartItems [id=" + id + ", price=" + price + ", quantity=" + quantity + ", product=" + product
				+ ", user=" + user + ", order=" + order + "]";
	}

}
