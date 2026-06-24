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
	private static final org.slf4j.Logger log =
			org.slf4j.LoggerFactory.getLogger(UserController.class);
	private static final String REDIRECT_ADMIN_SERVICES = "redirect:/admin/services";

	@Autowired
	private UserServices services;

	@PostMapping("/addingUser")
	public String  addUser(@ModelAttribute User user)
	{
		log.info("{}", user);
		this.services.addUser(user);
		return REDIRECT_ADMIN_SERVICES;
	}

	@GetMapping("/updatingUser/{id}")
	public String updateUser(@ModelAttribute User user, @PathVariable("id") int id)
	{
		this.services.updateUser(user, id);
		return REDIRECT_ADMIN_SERVICES;
	}

	@GetMapping("/deleteUser/{id}")
	public String deleteUser(@PathVariable("id" )int id)
	{
		this.services.deleteUser(id);
		return REDIRECT_ADMIN_SERVICES;
	}
	


}