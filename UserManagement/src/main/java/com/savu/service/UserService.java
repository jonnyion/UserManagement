package com.savu.service;


import java.util.List;
import java.util.Optional;

import com.savu.model.User;

public interface UserService {
	
	Optional<User> findById(Long id);

	User findByName(String name);

	void saveUser(User user);

	void updateUser(User user);

	void deleteUserById(Long id);

	void deleteAllUsers();

	List<User> findAllUsers();

	boolean isUserExist(User user);
}