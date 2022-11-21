package com.lcwd.user.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.user.service.entity.User;
import com.lcwd.user.service.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	// create

	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User user1 = userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}

	// get single user

	@GetMapping("/{userId}")
	public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
		User user2 = userService.getUser(userId);
		return ResponseEntity.ok(user2);
	}

	// get multiple user

	@GetMapping
	public ResponseEntity<List<User>> getAllUser() {
		List<User> user3 = userService.getAllUser();
		return ResponseEntity.ok(user3);
	}

	@PutMapping
	public ResponseEntity<User> updateUser(@PathVariable String userId) {
		User updateUser = userService.updateUser(userId);
		return ResponseEntity.ok(updateUser);
	}
}
