package it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Credentials;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Task;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository.CredentialsRepository;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionData {

	private User user;

	private Credentials credentials;

	private Project project;

	private Task task;

	@Autowired
	private CredentialsRepository credentialsRepository;

	public Credentials getLoggedCredentials() {
		if(this.credentials == null)
			this.update();
		return this.credentials;
	}

	public User getLoggedUser() {
		if(this.user == null)
			this.update();
		return this.user;
	}

	public Project getCurrentProject() {
		return this.project;
	}

	public void setCurrentProject(Project project) {
		this.project = project;
	}

	public Task getCurrentTask() {
		return this.task;
	}

	public void setCurrentTask(Task task) {
		this.task = task;
	}

	private void update() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails loggedUserDetails = (UserDetails) obj;
		this.credentials = this.credentialsRepository.findByUsername(loggedUserDetails.getUsername()).get();
		this.credentials.setPassword("[PROTECTED]");
		this.user = this.credentials.getUser();
	}

}
