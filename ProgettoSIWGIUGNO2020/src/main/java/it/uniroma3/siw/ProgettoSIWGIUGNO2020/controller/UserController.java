package it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.session.SessionData;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.validation.UserValidator;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Credentials;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository.UserRepository;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.CredentialsService;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserValidator userValidator;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	CredentialsService credentialsService;

	@Autowired
	SessionData sessionData;

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "home";
	}

	@RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
	public String admin(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("user", loggedUser);
		return "admin";
	}

	@RequestMapping(value = { "/users/me" }, method = RequestMethod.GET)
	public String me(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Credentials credentials = sessionData.getLoggedCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentials", credentials);

		return "userProfile";
	}

	@RequestMapping(value = { "/admin/users" }, method = RequestMethod.GET)
	public String userList(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Credentials> allCredentials = this.credentialsService.getAllCredentials();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("credentialsList", allCredentials);
		return "allUsers";
	}

	@RequestMapping(value = { "/admin/users/{username}/delete" }, method = RequestMethod.POST)
	public String removeUser(Model model, @PathVariable String username) {
		this.credentialsService.deleteCredentials(username);
		return "redirect:/admin/users";
	}

}


