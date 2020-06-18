package it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.session.SessionData;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.controller.validation.ProjectValidator;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.ProjectService;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.TagService;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.UserService;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Tag;

@Controller
public class ProjectController {

	@Autowired
	TagService tagService;
	
	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	ProjectValidator projectValidator;

	@Autowired
	SessionData sessionData;

	@RequestMapping(value = { "/projects" }, method = RequestMethod.GET)
	public String myOwnedProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.retrieveProjectsOwnedBy(loggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		return "myOwnedProjects";
	}

	@RequestMapping(value = { "/visibleProjects" }, method = RequestMethod.GET)
	public String myVisibleProjects(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		List<Project> projectsList = projectService.retrieveProjectsVisibleBy(loggedUser);
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectsList", projectsList);
		return "myVisibleProjects";
	}

	@RequestMapping(value = { "/projects/{projectId}" }, method = RequestMethod.GET)
	public String project(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		Project project = projectService.getProject(projectId);
		if(project == null)
			return "redirect:/projects";
		List<User> members = userService.getMembers(project);
		if(!project.getOwner().equals(loggedUser) && !members.contains(loggedUser))
			return "redirect:/projects";
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("project", project);
		model.addAttribute("members", members);
		return "project";
	}

	@RequestMapping(value = { "/projects/add" }, method = RequestMethod.GET)
	public String createProjectForm(Model model) {
		User loggedUser = sessionData.getLoggedUser();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		return "addProject";
	}

	@RequestMapping(value = { "/projects/add" }, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("projectForm") Project project,
			BindingResult projectBindingResult,
			Model model) {
		User loggedUser = sessionData.getLoggedUser();
		projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			project.setOwner(loggedUser);
			this.projectService.saveProject(project);
			return "redirect:/projects/" + project.getId();
		}
		model.addAttribute("loggedUser", loggedUser);
		return "addProject";
	}

	@RequestMapping(value = { "/projects/{projectId}/editProject" }, method = RequestMethod.GET)
	public String editProjectForm(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		sessionData.setCurrentProject(this.projectService.getProject(projectId));
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("projectForm", new Project());
		return "editProject";
	}

	@RequestMapping(value = { "/projects/{projectId}/editProject" }, method = RequestMethod.POST)
	public String editProject(@Valid @ModelAttribute("projectForm") Project project,
			BindingResult projectBindingResult,
			Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Project daModificare = sessionData.getCurrentProject();
		projectValidator.validate(project, projectBindingResult);
		if(!projectBindingResult.hasErrors()) {
			daModificare.setName(project.getName());
			this.projectService.saveProject(daModificare);
			return "redirect:/projects/" + daModificare.getId();
		}
		model.addAttribute("loggedUser", loggedUser);
		return "editProject";
	}
	
	@RequestMapping(value = { "/projects/{projectId}/delete" }, method = RequestMethod.POST)
	public String removeProject(Model model, @PathVariable Long projectId) {
		this.projectService.deleteProject(this.projectService.getProject(projectId));
		return "redirect:/projects";
	}
	
	@RequestMapping(value = { "/projects/{projectId}/addTag" }, method = RequestMethod.GET)
	public String addTagForm(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		sessionData.setCurrentProject(this.projectService.getProject(projectId));
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("tagForm", new Tag());
		return "addTag";
	}

	@RequestMapping(value = { "/projects/{projectId}/addTag" }, method = RequestMethod.POST)
	public String addTag(@Valid @ModelAttribute("tagForm") Tag tag,
			Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Project currentProject = sessionData.getCurrentProject();
		this.tagService.saveTag(tag);
		this.projectService.addTag(currentProject, tag);
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:/projects/" + currentProject.getId();
	}

}
