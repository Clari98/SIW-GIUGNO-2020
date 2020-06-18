package it.uniroma3.siw.ProgettoSIWGIUGNO2020.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(length = 2000)
	private String description;

	@Column(nullable = false, updatable = false)
	private LocalDate creationDate;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	private List<Comment> comments;

	@ManyToMany(mappedBy = "tasks")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Tag> tags;

	@OneToOne
	private User user;

	public Task() {
		this.comments = new ArrayList<>();
		this.tags = new ArrayList<>();
	}

	public Task(String name, String description) {
		this();
		this.name = name;
		this.description = description;
	}

	@PrePersist
	protected void onPersist() {
		this.creationDate = LocalDate.now();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Comment> getComments(){
		return this.comments;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Tag> getTags(){
		return this.tags;
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	@Override
	public boolean equals(Object o) {
		Task that = (Task) o;
		return this.name.equals(that.name) && this.description.equals(that.description) &&
				this.creationDate.equals(that.creationDate);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.description.hashCode() + this.creationDate.hashCode();
	}

}
