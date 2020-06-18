package it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.*;


public interface UserRepository extends CrudRepository<User, Long>{

	public List<User> findByVisibleProjects(Project project);
}
