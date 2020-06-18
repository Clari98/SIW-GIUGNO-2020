package it.uniroma3.siw.ProgettoSIWGIUGNO2020.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 2000)
	private String description;

	public Comment() {}

	public Comment(String description) {
		this();
		this.description = description;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	@Override
	public boolean equals(Object o) {
		Comment that = (Comment) o;
		return this.description.equals(that.description);
	}

	@Override
	public int hashCode() {
		return this.description.hashCode();
	}

}
