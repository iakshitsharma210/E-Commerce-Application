package com.example.demo.dtos;

import lombok.Data;

@Data
public class PlaceOrderDto {

	private Long userId;
	private String address;
	private String orderDescription;
	private String payment;
	private String mobile;

	@Override
	public String toString() {
		return "PlaceOrderDto [userId=" + userId + ", address=" + address + ", orderDescription=" + orderDescription
				+ ", payment=" + payment + ", mobile=" + mobile + "]";
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
