package com.eshop.project.ui.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eshop.project.api.entities.User;
import com.eshop.project.api.services.UserService;
import com.eshop.project.security.UserSecurity;

@Controller
public class ProfileController {

	@Autowired
	private UserService userService;

	@GetMapping("/profile")
	public String viewDetails(@AuthenticationPrincipal UserSecurity currentUser, Model model) {
		String email = currentUser.getUsername();
		User user = userService.getUserByEmail(email);
		model.addAttribute("user", user);
		return "profile-form.html";

	}

	@PostMapping("/profile/update")
	public String updateProfile(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal UserSecurity currentUser) {
		boolean isActive = user.isActive();
		User updatedProfile = userService.updateProfile(user);
		currentUser.setFirstName(updatedProfile.getFirstName());
		currentUser.setlastName(updatedProfile.getLastName());
		if (!isActive)
			return "redirect:/login";
		redirectAttributes.addFlashAttribute("message", "PROFILE HAS BEEN UPDATED SUCCESSFULLY");
		return "redirect:/profile";

	}

}