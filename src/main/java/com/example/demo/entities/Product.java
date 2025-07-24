package com.example.demo.entities;

import java.util.Arrays;

import com.example.demo.dtos.ProductDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", brand=" + brand + ", price="
				+ price + ", stockQuantity=" + stockQuantity + ", imageData=" + Arrays.toString(imageData) + "]";
	}

	public ProductDto getProductDto() {
	    ProductDto dto = new ProductDto();
	    dto.setId(this.getId());
	    dto.setName(this.getName());
	    dto.setDescription(this.getDescription());
	    dto.setBrand(this.getBrand());
	    dto.setPrice(this.getPrice());
	    dto.setStockQuantity(this.getStockQuantity());
	    dto.setImageData(this.getImageData());
	    return dto;
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
