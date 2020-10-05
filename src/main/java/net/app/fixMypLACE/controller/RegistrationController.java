package net.app.fixMypLACE.controller;

import net.app.fixMypLACE.dto.User;

import net.app.fixMypLACE.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegistrationController {

	private final UserService userService;

	@Autowired
	public RegistrationController(UserService userService) {
		this.userService = userService;

	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {

		model.addAttribute("user", new User());
		model.addAttribute("title", "Sign up");
		return "/registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String createNewUser(@Valid @ModelAttribute User user,
			BindingResult bindingResult, Model model) {
		// String[] numbers = {"0","27"};
		String[] words = { "fuck", "pussy", "marete", "kuku", "dick" };

		String[] accepted = { "gmail.com", "yahoo.com", "webmail.com" };

		if (userService.findByEmail(user.getEmail()).isPresent()) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (userService.findByUsername(user.getUsername()).isPresent()) {
			bindingResult
					.rejectValue("username", "error.user",
							"There is already a user registered with the username provided");
		}

		// for(String a:numbers){
		// if(!user.getContactNumber().startsWith(a)){
		//
		// bindingResult
		// .rejectValue("contactNumber", "error.user",
		// "Your email host is not allowed to use this system");
		//
		// } else{
		//
		// }
		// }
		//

		// for(String a:accepted){
		// if(user.getUsername().contains(a)){
		//
		// bindingResult.rejectValue("email", "error.user",
		// "username cant be set to "+user.getUsername());
		// }
		//
		//
		// }

		for (String w : words) {
			if (user.getUsername().contains(w)) {

				bindingResult.rejectValue("username", "error.user",
						"username cant be set to " + user.getUsername());
			}

		}

		model.addAttribute("title", "Sign up errors");
		if (!bindingResult.hasErrors()) {
			// Registration successful, save user
			// Set user role to USER and set it as active
			// User p = new User();
			// p.getFirst_name().equals(user.getFirst_name().trim().toLowerCase());
			// p.getLastName().equals(user.getLastName().trim().toLowerCase());
			// p.getEmail().equals(user.getEmail().trim().toLowerCase());
			// p.getUsername().equals(user.getUsername().trim().toLowerCase());
			// p.getCity().equals(user.getCity().trim().toLowerCase());
			// p.getAddressLineOne().equals(user.getAddressLineOne().trim().toLowerCase());
			// p.getAddressLineTwo().equals(user.getAddressLineTwo().trim().toLowerCase());
			// p.getContactNumber().equals(user.getContactNumber());
			// p.getPassword().equals(user.getPassword());
			// p.getPostalCode().equals(user.getPostalCode());
			//
			userService.save(user);
			model.addAttribute("successMessage",
					"User has been registered successfully");
			model.addAttribute("user", new User());
			model.addAttribute("title", "Sign up success");
		}
		return "/registration";
	}

}