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
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Tag;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Task;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.ProjectService;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.TagService;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.service.TaskService;

@Controller
public class TaskController {

	@Autowired
	SessionData sessionData;

	@Autowired
	TaskService taskService;

	@Autowired
	ProjectService projectService;

	@Autowired
	TagService tagService;

	@RequestMapping(value = { "/projects/{projectId}/tasks/addTask" }, method = RequestMethod.GET)
	public String createTaskForm(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		sessionData.setCurrentProject(this.projectService.getProject(projectId));
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("taskForm", new Task());
		return "addTask";
	}

	@RequestMapping(value = { "/projects/{projectId}/tasks/addTask" }, method = RequestMethod.POST)
	public String createProject(@Valid @ModelAttribute("taskForm") Task task, @PathVariable Long projectId,
			Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Project currentProject = sessionData.getCurrentProject();
		this.taskService.saveTask(task);
		currentProject.addTask(task);
		this.projectService.saveProject(currentProject);
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:/projects/" + currentProject.getId() + "/tasks/" + task.getId();
	}

	@RequestMapping(value = { "/projects/{projectId}/tasks" }, method = RequestMethod.GET)
	public String projectTasks(Model model, @PathVariable Long projectId) {
		User loggedUser = sessionData.getLoggedUser();
		List<Task> tasksList = this.projectService.getProject(projectId).getTasks();
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("tasksList", tasksList);
		return "tasks";
	}

	@RequestMapping(value = { "/projects/{projectId}/tasks/{taskId}" }, method = RequestMethod.GET)
	public String task(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		User loggedUser = sessionData.getLoggedUser();
		Task task = taskService.getTask(taskId);
		if(task == null)
			return "redirect:/projects/" + projectId + "/tasks";
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("task", task);
		return "task";
	}

	@RequestMapping(value = { "/projects/{projectId}/tasks/{taskId}/addTagToTask" }, method = RequestMethod.GET)
	public String addTagForm(Model model, @PathVariable Long projectId, @PathVariable Long taskId) {
		User loggedUser = sessionData.getLoggedUser();
		sessionData.setCurrentProject(this.projectService.getProject(projectId));
		sessionData.setCurrentTask(this.taskService.getTask(taskId));
		model.addAttribute("loggedUser", loggedUser);
		model.addAttribute("tagForm", new Tag());
		return "addTagToTask";
	}

	@RequestMapping(value = { "/projects/{projectId}/tasks/{taskId}/addTagToTask" }, method = RequestMethod.POST)
	public String addTag(@Valid @ModelAttribute("tagForm") Tag tag,
			Model model) {
		User loggedUser = sessionData.getLoggedUser();
		Task currentTask = sessionData.getCurrentTask();
		Project currentProject = sessionData.getCurrentProject();
		this.tagService.saveTag(tag);
		this.taskService.addTag(currentTask, tag);
		this.tagService.addTask(tag, currentTask);
		model.addAttribute("loggedUser", loggedUser);
		return "redirect:/projects/" + currentProject.getId() + "/tasks/" + currentTask.getId();
	}

}
