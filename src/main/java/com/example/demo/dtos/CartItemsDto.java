package com.example.demo.dtos;

import java.util.Arrays;

import lombok.Data;

@Data
public class CartItemsDto {
	private Long id;
	private Long price;
	private Long quantity;
	private Long productId;
	private Long userId;
	private Long orderId;
	private String productName;
	private byte[] imageData;
	
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	@Override
	public String toString() {
		return "CartItemsDto [id=" + id + ", price=" + price + ", quantity=" + quantity + ", productId=" + productId
				+ ", userId=" + userId + ", orderId=" + orderId + ", productName=" + productName + ", imageData="
				+ Arrays.toString(imageData) + "]";
	}
}
