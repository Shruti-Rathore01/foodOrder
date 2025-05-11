package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.entities.Admin;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Component
@Service
public class UserServices 
{
	@Autowired
	private UserRepository userRepository;

	public List<User> getAllUser()
	{
		List<User> users = (List<User>) this.userRepository.findAll();
		return users;
	}

	public User getUser(int id)
	{
		Optional<User> optional = this.userRepository.findById(id);
		User user = optional.get();
		return user;
	}
	public User getUserByEmail(String email)
	{
	 User user=	this.userRepository.findUserByUemail(email);
	 return user;
	}

	public void updateUser(User user,int id)
	{
		user.setU_id(id);
		 this.userRepository.save(user);
	}

	public void deleteUser(int id)
	{
		this.userRepository.deleteById(id);
	}

	public void addUser(User user)
	{
	this.userRepository.save(user);
	}

	public boolean validateLoginCredentials(String email, String password) {
		List<User> users = (List<User>) this.userRepository.findAll();

		for (User user : users) {
			if (user != null) {
				String dbPassword = user.getUpassword();
				String dbEmail = user.getUemail();

				if (dbEmail != null && dbEmail.equals(email)) {
					if (dbPassword == null) {
						System.out.println("Password is null for email: " + email);
						return false;  // Password is null, cannot proceed with comparison
					}

					if (dbPassword.equals(password)) {
						return true;
					}
				}
			}
		}

		return false;
	}


	public User findUserByUemail(String uemail) {
		try {
			return userRepository.findUserByUemail(uemail);
		} catch (Exception e) {
			System.out.println("Exception in findUserByUemail: " + e.getMessage());
			return null;
		}
	}
}