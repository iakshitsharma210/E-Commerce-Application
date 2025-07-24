package com.example.demo.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.CartItemsDto;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.PlaceOrderDto;
import com.example.demo.dtos.ProductDto;

@Service
public interface CustomerService {

	List<ProductDto> getAllProducts();

	ResponseEntity<?> addProductToCart(CartItemsDto cartItemDto);

	OrderDto getCartByUserId(Long userId);
	
	OrderDto addMinusOnProduct(Long userId,Long productId);
	
	OrderDto addPlusOnProduct(Long userId,Long productId);
	
	OrderDto placeOrder(PlaceOrderDto placeOrderDto);


}
