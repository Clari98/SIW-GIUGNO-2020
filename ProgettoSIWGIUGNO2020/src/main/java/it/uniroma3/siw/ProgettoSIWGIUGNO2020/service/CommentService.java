package it.uniroma3.siw.ProgettoSIWGIUGNO2020.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.ProgettoSIWGIUGNO2020.model.Comment;
import it.uniroma3.siw.ProgettoSIWGIUGNO2020.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	protected CommentRepository commentRepository;
	
	@Transactional
	public void saveComment(Comment comment) {
		this.commentRepository.save(comment);
	}
	
	@Transactional
	public Comment getComment(Long id) {
		Optional<Comment> result = this.commentRepository.findById(id);
		return result.orElse(null);
	}


}
