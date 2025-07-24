package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;

@Service
public interface AdminService {

	List<ProductDto> getAllProducts();

	Product getProductById(Long productId);

	Product addProduct(Product product, MultipartFile imageFile) throws IOException;

	Product updateProduct(Long productId, Product product, MultipartFile imageFile) throws IOException;

	void deleteProductById(Long productId);

	ProductDto updateProduct(Long productId, ProductDto productDto);
}
