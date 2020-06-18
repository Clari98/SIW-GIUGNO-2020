package it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;

@Component
public class ProjectValidator implements Validator {

	final Integer MAX_NAME_LENGTH = 100;
	final Integer MIN_NAME_LENGTH = 2;

	@Override
	public void validate(Object o, Errors errors) {
		Project project = (Project) o;
		String name = project.getName().trim();
		if(name.isBlank())
			errors.rejectValue("name", "required");
		else if(name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH)
			errors.rejectValue("name", "size");
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz);
	}
}
