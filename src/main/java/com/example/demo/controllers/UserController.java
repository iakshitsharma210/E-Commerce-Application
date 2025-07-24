package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		if (service.findUserByUsername(user.getUsername())) {
			return new ResponseEntity<>("username name already exists", HttpStatus.BAD_REQUEST);
		}
		if (service.hasUserWithEmail(user.getEmail())) {
			return new ResponseEntity<>("User email already exists", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(service.register(user), HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
		return service.verify(user, response);
	}
}
