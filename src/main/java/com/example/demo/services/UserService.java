package com.example.demo.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Order;
import com.example.demo.entities.User;
import com.example.demo.enums.OrderStatus;
import com.example.demo.enums.UserRole;
import com.example.demo.repos.OrderRepo;
import com.example.demo.repos.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private OrderRepo orderRepo;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	private JWTService jwtService;

	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String HEADER_STRING = "Authorization";

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public User register(User user) {
		user.setUserRole(UserRole.USER);
		user.setPassword(encoder.encode(user.getPassword()));
		User savedUser = userRepo.save(user);
		Order order = new Order();
		order.setPrice(0L);
		order.setOrderStatus(OrderStatus.PENDING);
		order.setUser(savedUser);
		orderRepo.save(order);
		return savedUser;
	}

	public ResponseEntity<Map<String, Object>> verify(User user, HttpServletResponse response) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		if (authentication.isAuthenticated()) {
			String jwt = jwtService.generateToken(user.getUsername());
			User resUser = userRepo.findByUsername(user.getUsername());
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + jwt);
			headers.setAccessControlExposeHeaders(List.of("Authorization")); // exposes the Authorization header

			Map<String, Object> responseBody = new HashMap<>();
			responseBody.put("userId", resUser.getId());
			responseBody.put("role", resUser.getUserRole());

			return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Authentication failed"));
	}

	public boolean hasUserWithEmail(String email) {
		return userRepo.findByEmail(email) != null;
	}

	public boolean findUserByUsername(String username) {
		return userRepo.findByUsername(username) != null;
	}

}
