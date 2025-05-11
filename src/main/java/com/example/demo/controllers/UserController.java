package com.example.demo.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entities.User;
import com.example.demo.services.UserServices;

@Controller
public class UserController
{
	@Autowired
	private UserServices services;

	@PostMapping("/addingUser")
	public String addUser(@ModelAttribute("user") User user, Model model) {
		System.out.println("User to be registered: " + user);

		try {
			// Check if the email already exists
			User existingUser = services.getUserByEmail(user.getUemail());
			if (existingUser != null) {
				model.addAttribute("error", "Email is already registered.");
				return "register";  // Return to the registration page with error
			}

			// Save the new user
			services.addUser(user);
			return "redirect:/login";  // Redirect to the login page after successful registration

		} catch (Exception e) {
			System.out.println("Exception during registration: " + e.getMessage());
			model.addAttribute("error", "An error occurred during registration.");
			return "register";
		}
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "register";  // This should match the Thymeleaf template name (register.html)
	}

	@GetMapping("/addUserForm")
	public String showAddUserForm(Model model) {
		model.addAttribute("user", new User());
		return "Add_User";
	}
	@GetMapping("/updatingUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable("id") int id)
	{
		this.services.updateUser(user, id);
		return "redirect:/admin/services";
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id" )int id)
	{
		this.services.deleteUser(id);
		return "redirect:/admin/services";
	}

	}




