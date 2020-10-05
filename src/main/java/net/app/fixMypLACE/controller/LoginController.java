package net.app.fixMypLACE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;
import net.app.fixMypLACE.dto.User;
import net.app.fixMypLACE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

@Controller
public class LoginController {

	@Autowired
	UserService service;
        /**
         * Login user
         * @param principal
         * @param username
         * @param model
         * @return 
         */
	@GetMapping("/login")
	public String login(Principal principal, String username, Model model) {

		model.addAttribute("title", "Sign in");
		// model.addAttribute("lastLogin", );
		if (principal != null) {
			Optional<User> user = service.findByUsername(principal.getName());
			service.updateLastLogin(username);
			return "redirect:/home";
		}
		return "/login";
	}

}
