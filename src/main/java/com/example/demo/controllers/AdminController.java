package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.services.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/product")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> productDtoList = adminService.getAllProducts();
		return new ResponseEntity<>(productDtoList, HttpStatus.OK);
	}

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart("product") String productJson,
			@RequestPart("imageData") MultipartFile imageData) {
		try {
			Product product = objectMapper.readValue(productJson, Product.class);
			Product savedProduct = adminService.addProduct(product, imageData);
			return new ResponseEntity<>(savedProduct, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/product/{productId}")
	public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestPart("product") String productJson,
			@RequestPart(value = "imageData", required = false) MultipartFile imageData) {
		try {
			ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
			if (imageData != null && !imageData.isEmpty()) {
	            productDto.setImageData(imageData.getBytes());
	        }
			ProductDto updatedProductDto = adminService.updateProduct(productId, productDto);
			if (updatedProductDto == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<String> deleteProductById(@PathVariable Long productId) {
		Product product = adminService.getProductById(productId);
		if (product != null) {
			adminService.deleteProductById(productId);
			return new ResponseEntity<>("Delete Successful", HttpStatus.OK);
		}
		return new ResponseEntity<>("Product Not Forund", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
		Product product = adminService.getProductById(productId);
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

//	@PutMapping("/product/{productId}")
//	public ResponseEntity<?> updateProduct(@PathVariable Long productId, @ModelAttribute ProductDto productDto){
//		ProductDto updatedProductDto= adminService.updateProduct(productId, productDto);
//		if (updatedProductDto == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<>(updatedProductDto, HttpStatus.OK);
//	}

//	@GetMapping("/product/{productId}/image")
//	public ResponseEntity<byte[]> getImageByProductId(@PathVariable("productId") Long productId) {
//		Product product = adminService.getProductById(productId);
//		byte[] imageFile = product.getImageData();
//		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
//	}

//	@PutMapping("/product/{productId}")
//	public ResponseEntity<String> updateProduct(@PathVariable("productId") Long productId,
//			@RequestPart("product") String productJson, @RequestPart("imageFile") MultipartFile imageFile) {
//		Product product1 = null;
//		try {
//			Product product = objectMapper.readValue(productJson, Product.class);
//			product1 = adminService.updateProduct(productId, product, imageFile);
//		} catch (Exception e) {
//			return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
//		}
//		if (product1 != null) {
//			return new ResponseEntity<>("Update Successful", HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
//		}
//	}

}
