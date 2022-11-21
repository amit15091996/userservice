package com.lcwd.user.service.services;

import java.util.List;

import com.lcwd.user.service.entity.User;

public interface UserService {

	// user operations

	// create
	User saveUser(User user);

	// get All user
	List<User> getAllUser();

	// get single user
	User getUser(String userId);

	// update user
	User updateUser(String userId);

	// delete user
	void deleteUser(String userId);

}
