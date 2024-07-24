package com.users.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.users.dto.LoginRequest;
import com.users.dto.UpdatePassword;
import com.users.entity.Users;
import com.users.exception.InvalidPasswordException;
import com.users.exception.UserNotFoundException;
import com.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService usersService;

	@PostMapping("/create")
	public ResponseEntity<Users> createUser(@RequestBody Users user) {
		Users createdUser = usersService.createUser(user);
		return ResponseEntity.ok(createdUser);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		String result = usersService.deleteUser(id);
		return ResponseEntity.ok(result);
	}

	@PutMapping("/updateUser")
	public ResponseEntity<Users> updateUser(@RequestBody Users user) {
		Users userUpdate = usersService.updateUser(user);
		return ResponseEntity.ok(userUpdate);
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<String> changePassword(@RequestBody UpdatePassword updatePassword) {
		String response = usersService.changePassword(updatePassword);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<Optional<Users>> getuserById(@PathVariable Long id) {
		Optional<Users> findUser = usersService.getUserById(id);
		return ResponseEntity.ok(findUser);
	}

	@GetMapping("/getAllUsers")
	public ResponseEntity<List<Users>> getAllUsers() {
		List<Users> users = usersService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            Users user = usersService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException | InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
