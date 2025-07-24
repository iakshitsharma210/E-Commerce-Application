package com.example.demo.dtos;

import java.util.Date;
import java.util.List;

import com.example.demo.enums.OrderStatus;

import lombok.Data;

@Data
public class OrderDto {
	private Long id;
	private String description;
	private String address;
	private String paymentType;
	private Date date;
	private Long amount;
	private OrderStatus orderStatus;
	private Long userId;
	private String mobile;

	private List<CartItemsDto> cartItems;

	@Override
	public String toString() {
		return "OrderDto [id=" + id + ", description=" + description + ", address=" + address + ", paymentType="
				+ paymentType + ", date=" + date + ", amount=" + amount + ", orderStatus=" + orderStatus + ", userId="
				+ userId + ", mobile=" + mobile + ", cartItems=" + cartItems + "]";
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartItemsDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemsDto> cartItems) {
		this.cartItems = cartItems;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
