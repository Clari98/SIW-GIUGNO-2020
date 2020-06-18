package it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.UserService;

@Component
public class UserValidator implements Validator{

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;
		String firstname = user.getFirstname().trim();
		String lastname = user.getLastname().trim();
		if(firstname.isEmpty() || firstname.equals(" "))
			errors.rejectValue("firstname", "required");
		else if(firstname.length() < MIN_NAME_LENGTH || firstname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("firstname", "size");
		if(lastname.isEmpty() || lastname.equals(" "))
			errors.rejectValue("lastname", "required");
		else if(lastname.length() < MIN_NAME_LENGTH || lastname.length() > MAX_NAME_LENGTH)
			errors.rejectValue("lastname", "size");
	}


}
