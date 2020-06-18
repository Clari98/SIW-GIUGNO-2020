package it.uniroma3.siw.ProgettoSIWGIUGNO2020.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Comment;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Tag;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Task;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	protected TaskRepository taskRepository;

	@Transactional
	public Task getTask(Long id) {
		Optional<Task> result = this.taskRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Task saveTask(Task task) {
		return this.taskRepository.save(task);
	}

	@Transactional
	public void removeTask(Task task) {
		this.taskRepository.delete(task);
	}

	@Transactional
	public Task addTaskForUser(Task task, User user) {
		task.setUser(user);
		return this.taskRepository.save(task);
	}

	@Transactional
	public Task addTag(Task task, Tag tag) {
		task.addTag(tag);
		return this.taskRepository.save(task);
	}

	@Transactional
	public Task addComment(Task task, Comment comment) {
		task.addComment(comment);
		return this.taskRepository.save(task);
	}

}
