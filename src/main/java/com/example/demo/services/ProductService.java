package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Product;

@Service
public interface ProductService {

	List<Product> getAllProducts();

	Product addProduct(Product product, MultipartFile imageFile) throws IOException;

	Product updateProduct(Long productId, Product product, MultipartFile imageFile) throws IOException;

	void deleteProductById(Long productId);

	Product getProductById(Long id);

}
