package it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Project;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.User;

public interface ProjectRepository extends CrudRepository<Project, Long> {

	public List<Project> findByMembers(User member);

	public List<Project> findByOwner(User owner);

}
