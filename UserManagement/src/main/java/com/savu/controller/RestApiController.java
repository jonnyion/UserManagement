package com.savu.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.savu.model.Group;
import com.savu.model.User;
import com.savu.service.GroupService;
import com.savu.service.UserService;
import com.savu.util.CustomErrorType;

@RestController
@RequestMapping("/api")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	UserService userService;

	// -------------------Retrieve All
	// Users---------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// User------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		Optional<User> user = userService.findById(id);
		if (!user.isPresent()) {
			logger.error("User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
	}

	// -------------------Create a User-------------------------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.info("Creating User : {}", user);

		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with name {} already exist", user.getName());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A User with name " + user.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a User
	// ------------------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User with id {}", id);

		Optional<User> optionalUser = userService.findById(id);

		if (!optionalUser.isPresent()) {
			logger.error("Unable to update. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		User currentUser = optionalUser.get();
		currentUser.setName(user.getName());
		currentUser.setEmail(user.getEmail());
		currentUser.setGroups(user.getGroups());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	// ------------------- Delete a User-----------------------------------------

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);

		Optional<User> user = userService.findById(id);
		if (!user.isPresent()) {
			logger.error("Unable to delete. User with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Users-----------------------------

	@RequestMapping(value = "/user/", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteAllUsers() {
		logger.info("Deleting All Users");

		userService.deleteAllUsers();
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@Autowired
	GroupService groupService;

	// -------------------Retrieve All
	// Groups---------------------------------------------

	@RequestMapping(value = "/group/", method = RequestMethod.GET)
	public ResponseEntity<List<Group>> listAllGroups() {
		List<Group> groups = groupService.findAllGroups();
		if (groups.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}
		return new ResponseEntity<List<Group>>(groups, HttpStatus.OK);
	}

	// -------------------Retrieve Single
	// Group------------------------------------------

	@RequestMapping(value = "/group/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getGroup(@PathVariable("id") long id) {
		logger.info("Fetching Group with id {}", id);
		Optional<Group> group = groupService.findById(id);
		if (!group.isPresent()) {
			logger.error("Group with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("User with id " + id + " not found"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Group>(group.get(), HttpStatus.OK);
	}

	// -------------------Create a Group-------------------------------------------

	@RequestMapping(value = "/group/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createGroup(@RequestBody Group group, UriComponentsBuilder ucBuilder) {
		logger.info("Creating Group : {}", group);

		if (groupService.isGroupExist(group)) {
			logger.error("Unable to create. A group with name {} already exist", group.getName());
			return new ResponseEntity(
					new CustomErrorType("Unable to create. A Group with name " + group.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		groupService.saveGroup(group);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/api/group/{id}").buildAndExpand(group.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Group

	@RequestMapping(value = "/group/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateGroup(@PathVariable("id") long id, @RequestBody Group group) {
		logger.info("Updating Group with id {}", id);

		Optional<Group> currentGroup = groupService.findById(id);

		if (!currentGroup.isPresent()) {
			logger.error("Unable to update. Group with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to upate. Group with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}

		currentGroup.get().setName(group.getName());

		groupService.updateGroup(currentGroup.get());
		return new ResponseEntity<Group>(currentGroup.get(), HttpStatus.OK);
	}

	// ------------------- Delete a Group-----------------------------------------

	@RequestMapping(value = "/group/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGroup(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Group with id {}", id);

		Optional<Group> group = groupService.findById(id);
		if (!group.isPresent()) {
			logger.error("Unable to delete. Group with id {} not found.", id);
			return new ResponseEntity(new CustomErrorType("Unable to delete. Group with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		groupService.deleteGroupById(id);
		return new ResponseEntity<Group>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Groups-----------------------------

	@RequestMapping(value = "/group/", method = RequestMethod.DELETE)
	public ResponseEntity<Group> deleteAllGroups() {
		logger.info("Deleting All Users");

		groupService.deleteAllGroups();
		return new ResponseEntity<Group>(HttpStatus.NO_CONTENT);
	}
}