package it.uniroma3.siw.ProgettoSIWGIUGNO2020.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Tag;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	protected ProjectRepository projectRepository;

	@Transactional
	public Project getProject(Long id) {
		Optional<Project> result = this.projectRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public Project saveProject(Project project) {
		return this.projectRepository.save(project);
	}

	@Transactional
	public void deleteProject(Project project) {
		this.projectRepository.delete(project);
	}

	@Transactional
	public Project shareProjectWithUser(Project project, User user) {
		project.addMember(user);
		return this.projectRepository.save(project);
	}

	@Transactional
	public Project addTag(Project project, Tag tag) {
		project.addTag(tag);
		return this.projectRepository.save(project);
	}

	@Transactional
	public List<Project> retrieveProjectsOwnedBy(User owner) {
		return this.projectRepository.findByOwner(owner);
	}

	@Transactional
	public List<Project> retrieveProjectsVisibleBy(User user){
		return this.projectRepository.findByMembers(user);
	}

}
