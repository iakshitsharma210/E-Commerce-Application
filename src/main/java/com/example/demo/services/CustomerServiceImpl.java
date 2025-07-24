package com.example.demo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.CartItemsDto;
import com.example.demo.dtos.OrderDto;
import com.example.demo.dtos.PlaceOrderDto;
import com.example.demo.dtos.ProductDto;
import com.example.demo.entities.CartItems;
import com.example.demo.entities.Order;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.enums.OrderStatus;
import com.example.demo.repos.CartItemsRepo;
import com.example.demo.repos.OrderRepo;
import com.example.demo.repos.ProductRepo;
import com.example.demo.repos.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	private CartItemsRepo cartItemsRepo;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailService emailService;

	@Override
	public List<ProductDto> getAllProducts() {
		return productRepo.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ResponseEntity<?> addProductToCart(CartItemsDto cartItemDto) {

		// Validate input
		if (cartItemDto.getUserId() == null || cartItemDto.getProductId() == null) {
			return new ResponseEntity<>("UserId or ProductId missing", HttpStatus.BAD_REQUEST);
		}
		Order pendingOrder = orderRepo.findByUserIdAndOrderStatus(cartItemDto.getUserId(), OrderStatus.PENDING);

		// check if already present
		Optional<CartItems> optionalCartItems = cartItemsRepo.findByUserIdAndProductIdAndOrderId(
				cartItemDto.getUserId(), cartItemDto.getProductId(), pendingOrder.getId());
		if (optionalCartItems.isPresent()) {
			CartItemsDto productAlreadyExists = new CartItemsDto();
			productAlreadyExists.setProductId(null);
			return new ResponseEntity<>(productAlreadyExists, HttpStatus.BAD_REQUEST);
		}

//		Order pendingOrder = orderRepo.findByUserIdAndOrderStatus(cartItemDto.getUserId(), OrderStatus.PENDING);
		Optional<Product> optionalProduct = productRepo.findById(cartItemDto.getProductId());
		Optional<User> optionalUser = userRepo.findById(cartItemDto.getUserId());
		if (optionalProduct.isPresent() && optionalUser.isPresent()) {
			Product product = optionalProduct.get();
			CartItems cartItems = new CartItems();
			cartItems.setProduct(product);
			cartItems.setUser(optionalUser.get());
			cartItems.setQuantity(1L);
			cartItems.setOrder(pendingOrder);
			cartItems.setPrice(product.getPrice());
			CartItems resCartItems = cartItemsRepo.save(cartItems);
			pendingOrder.setPrice(pendingOrder.getPrice() + cartItems.getPrice());
			pendingOrder.getCartItems().add(cartItems);
			orderRepo.save(pendingOrder);
			CartItemsDto updatedCartItemsDto = new CartItemsDto();
			updatedCartItemsDto.setId(resCartItems.getId());
			return new ResponseEntity<>(updatedCartItemsDto, HttpStatus.OK);
		}
		return new ResponseEntity<>("User Not Forund", HttpStatus.BAD_REQUEST);
	}

	@Override
	@Transactional
	public OrderDto getCartByUserId(Long userId) {
		Order pendingOrder = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		List<CartItemsDto> cartItemsDtoList = pendingOrder.getCartItems().stream().map(CartItems::getCartItemsDto)
				.collect(Collectors.toList());
		OrderDto orderDto = new OrderDto();
		orderDto.setCartItems(cartItemsDtoList);
		orderDto.setAmount(pendingOrder.getPrice());
		orderDto.setId(pendingOrder.getId());
		orderDto.setOrderStatus(pendingOrder.getOrderStatus());
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto addMinusOnProduct(Long userId, Long productId) {
		Order pendingOrder = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		if (pendingOrder == null) {
			return null;
		}
		Optional<Product> optionalProduct = productRepo.findById(productId);
		Optional<CartItems> optionalCartItem = cartItemsRepo.findByUserIdAndProductIdAndOrderId(userId, productId,
				pendingOrder.getId());

		if (optionalCartItem.isPresent() && optionalProduct.isPresent()) {
			CartItems cartItem = optionalCartItem.get();
			cartItem.setQuantity(optionalCartItem.get().getQuantity() - 1);
			pendingOrder.setPrice(pendingOrder.getPrice() - optionalProduct.get().getPrice());
			cartItemsRepo.save(cartItem);
			orderRepo.save(pendingOrder);
			return pendingOrder.getOrderDto();
		}
		return null;
	}

	@Transactional
	@Override
	public OrderDto addPlusOnProduct(Long userId, Long productId) {
		Order pendingOrder = orderRepo.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
		if (pendingOrder == null) {
			return null;
		}
		Optional<Product> optionalProduct = productRepo.findById(productId);
		Optional<CartItems> optionalCartItem = cartItemsRepo.findByUserIdAndProductIdAndOrderId(userId, productId,
				pendingOrder.getId());

		if (optionalCartItem.isPresent() && optionalProduct.isPresent()) {
			CartItems cartItem = optionalCartItem.get();
			cartItem.setQuantity(optionalCartItem.get().getQuantity() + 1);
			pendingOrder.setPrice(pendingOrder.getPrice() + optionalProduct.get().getPrice());
			cartItemsRepo.save(cartItem);
			orderRepo.save(pendingOrder);
			return pendingOrder.getOrderDto();
		}
		return null;
	}

	@Override
	@Transactional
	public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
		Optional<User> optionalUser = userRepo.findById(placeOrderDto.getUserId());
		if (optionalUser.isEmpty() || placeOrderDto.getMobile()==null) {
			return null;
		}
		Order existingOrder = orderRepo.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);
		existingOrder.setOrderStatus(OrderStatus.SUBMITTED);
		existingOrder.setAddress(placeOrderDto.getAddress());
		existingOrder.setDate(new Date());
		existingOrder.setPaymentType(placeOrderDto.getPayment());
		existingOrder.setDescription(placeOrderDto.getOrderDescription());
		existingOrder.setMobile(placeOrderDto.getMobile());
		
		List<CartItems> cartItems = cartItemsRepo.findByOrderId(existingOrder.getId());
	    for (CartItems item : cartItems) {
	        Product product = item.getProduct();
	        Long currentStock = product.getStockQuantity();
	        Long quantityToDeduct = item.getQuantity();

	        if (currentStock < quantityToDeduct) {
	            throw new RuntimeException("Insufficient stock for product: " + product.getName());
	        }

	        product.setStockQuantity(currentStock - quantityToDeduct);
	        productRepo.save(product);
	    }
		orderRepo.save(existingOrder);
		//update product quantity in Product table
		
		// Send email
	    String userEmail = optionalUser.get().getEmail();  // ensure your User entity has getEmail()
	    emailService.sendOrderConfirmation(userEmail, existingOrder);
	    
	    // ✅ Create new PENDING order for user
		Order order = new Order();
		order.setOrderStatus(OrderStatus.PENDING);
		order.setUser(optionalUser.get());
		order.setPrice(0L);
		orderRepo.save(order);
		return order.getOrderDto();
	}

}
