package com.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.users.dto.UpdatePassword;
import com.users.entity.Users;
import com.users.exception.GlobalExceptionHandler;
import com.users.exception.InvalidPasswordException;
import com.users.exception.UserNotFoundException;
import com.users.general.IUserMetods;
import com.users.repository.UserRepository;

@Service
public class UserService implements IUserMetods {

	@Autowired
	private UserRepository usersRepository;

	@Autowired
	private GlobalExceptionHandler exeptionHandler;

	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Override
	public Users createUser(Users user) {

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return usersRepository.save(user);

	}

	@Override
	public String deleteUser(Long id) {
		Optional<Users> user = usersRepository.findById(id);
		if (user.isPresent()) {
			usersRepository.delete(user.get());
			return "user deleted successfully";
		} else {
			return "Error user can't be deleted";

		}
	}

	@Override
	public Users updateUser(Users user) {
		return usersRepository.save(user);
	}

	@Override
	public List<Users> getAllUsers() {

		return usersRepository.findAll();
	}

	@Override
	public Optional<Users> getUserById(Long id) {

		return usersRepository.findById(id);
	}

	@Override
	public Users loginUser(String username, String password) {
		Optional<Users> userOptional = usersRepository.findByUserName(username);

		
			if (!userOptional.isPresent()) {
				throw new UserNotFoundException("User not found");
			}

			Users user = userOptional.get();
			if (!passwordEncoder.matches(password, user.getPassword())) {
				 throw new InvalidPasswordException("Password does not match");

			}
			return user;
		
		
	}

	@Override
	public String changePassword(UpdatePassword updatePassword) {
		Optional<Users> userOptional = usersRepository.findById(updatePassword.getId());
		String message;
		if (!userOptional.isPresent()) {
			message = "User not found";
		}
		Users user = userOptional.get();
		if (passwordEncoder.matches(updatePassword.getPassword(), user.getPassword())) {
			message = "Can´t use the same password";
		}
		user.setPassword(passwordEncoder.encode(updatePassword.getPassword()));
		usersRepository.save(user);
		message = "Password change successfully";
		return message;
	}

}
