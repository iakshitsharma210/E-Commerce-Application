package com.example.demo.dtos;

import java.util.Arrays;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ProductDto {
	private Long id;
	private String name;
	private String description;
	private String brand;
	private Long price;
	private Long stockQuantity;
	@Lob
	private byte[] imageData;

	@Override
	public String toString() {
		return "ProductDto [id=" + id + ", name=" + name + ", description=" + description + ", brand=" + brand
				+ ", price=" + price + ", stockQuantity=" + stockQuantity + ", imageData=" + Arrays.toString(imageData)
				+ "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Long stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
}
