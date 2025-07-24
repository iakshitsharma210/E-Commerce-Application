package com.example.demo.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.repos.ProductRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ProductRepo productRepo;

	@Override
	public List<ProductDto> getAllProducts() {
		return productRepo.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	public Product getProductById(Long id) {
		return productRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product addProduct(Product product, MultipartFile imageData) throws IOException {
		product.setImageData(imageData.getBytes());
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

	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) {
		Optional<Product> optionalProduct = productRepo.findById(productId);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setDescription(productDto.getDescription());
			product.setPrice(productDto.getPrice());
			product.setStockQuantity(productDto.getStockQuantity());
			if (productDto.getImageData() != null) {
				product.setImageData(productDto.getImageData());
			}
			Product updatedProduct= productRepo.save(product);
			ProductDto updatedProductDto=new ProductDto();
			updatedProductDto.setId(updatedProduct.getId());
			return updatedProductDto;
		}
		return null;
	}

}
