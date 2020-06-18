package it.uniroma3.siw.ProgettoSIWGIUGNO2020.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Tag;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Task;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository.TagRepository;

@Service
public class TagService {

	@Autowired
	protected TagRepository tagRepository;

	@Transactional
	public Tag getTag(Long id) {
		Optional<Tag> result = this.tagRepository.findById(id);
		return result.orElse(null);
	}

	@Transactional
	public void saveTag(Tag tag) {
		this.tagRepository.save(tag);
	}

	@Transactional
	public Tag addTask(Tag tag, Task task) {
		tag.addTask(task);
		return this.tagRepository.save(tag);
	}

}
