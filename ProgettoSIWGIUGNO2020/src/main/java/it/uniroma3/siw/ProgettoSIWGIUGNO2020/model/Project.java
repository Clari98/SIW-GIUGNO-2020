package it.uniroma3.siw.ProgettoSIWGIUGNO2020.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, updatable = false)
	private LocalDate initialDate;

	@ManyToOne(fetch = FetchType.EAGER)
	private User owner;

	@ManyToMany
	private List<User> members;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id")
	private List<Task> tasks;

	@OneToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Tag> tags;

	public Project() {
		this.members = new ArrayList<>();
		this.tasks = new ArrayList<>();
		this.tags = new ArrayList<>();
	}

	@PrePersist
	protected void onPersist() {
		this.initialDate = LocalDate.now();
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

	public void setInitialDate(LocalDate initialDate) {
		this.initialDate = initialDate;
	}

	public LocalDate getInitialDate() {
		return this.initialDate;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public User getOwner() {
		return this.owner;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public List<User> getMembers(){
		return this.members;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Task> getTasks(){
		return this.tasks;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Tag> getTags(){
		return this.tags;
	}

	public void addMember(User member) {
		this.members.add(member);
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	@Override
	public boolean equals(Object o) {
		Project that = (Project) o;
		return this.name.equals(that.name) && this.initialDate.equals(that.initialDate);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.initialDate.hashCode();
	}

}
