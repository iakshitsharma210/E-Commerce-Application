package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.CartItemsDto;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.PlaceOrderDto;
import com.example.demo.dtos.ProductDto;
import com.example.demo.services.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/product")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		List<ProductDto> productDtoList = customerService.getAllProducts();
		return new ResponseEntity<>(productDtoList, HttpStatus.OK);
	}

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestBody CartItemsDto cartItemDto) {
		return customerService.addProductToCart(cartItemDto);
	}

	@GetMapping("/cart/{userId}")
	public ResponseEntity<?> getCartByUserId(@PathVariable Long userId) {
		OrderDto orderDto = customerService.getCartByUserId(userId);
		if (orderDto == null) {
			return new ResponseEntity<>("No Items in the Cart", HttpStatus.OK);
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}

	@PostMapping("/{userId}/decrement/{prouctId}")
	public ResponseEntity<?> addMinusOnProduct(@PathVariable Long userId, @PathVariable Long prouctId) {
		OrderDto orderDto = customerService.addMinusOnProduct(userId, prouctId);
		if(orderDto==null) {
			return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}

	@PostMapping("/{userId}/increment/{prouctId}")
	public ResponseEntity<?> addPlusOnProduct(@PathVariable Long userId, @PathVariable Long prouctId) {
		OrderDto orderDto = customerService.addPlusOnProduct(userId, prouctId);
		if(orderDto==null) {
			return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}
	
	@PostMapping("/place-order")
	public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
		OrderDto orderDto = customerService.placeOrder(placeOrderDto);
		if(orderDto==null) {
			return new ResponseEntity<>("Invalid Data", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(orderDto, HttpStatus.OK);
	}
	
	
	
}
