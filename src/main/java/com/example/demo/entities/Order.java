package com.example.demo.entities;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dtos.CartItemsDto;
import com.example.demo.dtos.OrderDto;
import com.example.demo.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private String address;
	private String paymentType;
	private Date date;
	private Long price;
	private OrderStatus orderStatus;
	@Column(length = 10, nullable = true)
	@Pattern(regexp = "\\d{10}", message = "Mobile number must be exactly 10 digits")
	private String mobile;

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
	private List<CartItems> cartItems;
	
	public OrderDto getOrderDto() {
	    OrderDto orderDto = new OrderDto();

	    orderDto.setId(this.getId());
	    orderDto.setDescription(this.getDescription());
	    orderDto.setAddress(this.getAddress());
	    orderDto.setPaymentType(this.getPaymentType());
	    orderDto.setDate(this.getDate());
	    orderDto.setAmount(this.getPrice());
	    orderDto.setOrderStatus(this.getOrderStatus());
	    orderDto.setMobile(this.getMobile());

	    if (this.getUser() != null) {
	        orderDto.setUserId(this.getUser().getId());
	    }

	    if (this.getCartItems() != null) {
	        List<CartItemsDto> cartItemsDtoList = this.getCartItems().stream()
	            .map(CartItems::getCartItemsDto)
	            .collect(Collectors.toList());
	        orderDto.setCartItems(cartItemsDtoList);
	    }

	    return orderDto;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", description=" + description + ", address=" + address + ", paymentType="
				+ paymentType + ", date=" + date + ", price=" + price + ", orderStatus=" + orderStatus + ", mobile="
				+ mobile + ", user=" + user + ", cartItems=" + cartItems + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	

	

}
