package com.users.general;

import java.util.List;
import java.util.Optional;

import com.users.dto.UpdatePassword;
import com.users.entity.Users;

public interface IUserMetods {
	public List<Users> getAllUsers();

	public Users createUser(Users user);

	public String deleteUser(Long id);

	public Users updateUser(Users user);

	public Optional<Users> getUserById(Long id);
	
	public Users loginUser(String username, String password);
	
	public String changePassword(UpdatePassword updatePassword);
}
