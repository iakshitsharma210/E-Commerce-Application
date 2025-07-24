package com.example.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Product;
import com.example.demo.repos.ProductRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepo productRepo;

	@Override
	public List<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product getProductById(Long id) {
		return productRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageData(imageFile.getBytes());
		return productRepo.save(product);
	}

	@Override
	@Transactional
	public Product updateProduct(Long productId, Product product, MultipartFile imageFile) throws IOException {
		product.setImageData(imageFile.getBytes());
		return productRepo.save(product);

	}

	@Override
	@Transactional
	public void deleteProductById(Long productId) {
		productRepo.deleteById(productId);
	}

}
