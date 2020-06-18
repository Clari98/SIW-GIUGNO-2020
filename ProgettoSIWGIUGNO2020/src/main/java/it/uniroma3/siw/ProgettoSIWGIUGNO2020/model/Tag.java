package it.uniroma3.siw.ProgettoSIWGIUGNO2020.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(length = 100)
	private String color;


	@Column(length = 2000)
	private String description;

	@ManyToMany
	private List<Task> tasks;

	public Tag() {
		this.tasks = new ArrayList<>();
	}

	public Tag(String name, String color, String description) {
		this();
		this.name = name;
		this.color = color;
		this.description = description;
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

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Task> getTasks(){
		return this.tasks;
	}

	public void addTask(Task task) {
		this.tasks.add(task);
	}

	@Override
	public boolean equals(Object o) {
		Tag that = (Tag) o;
		return this.name.equals(that.name) && this.color.equals(that.color) &&
				this.description.equals(that.description);
	}

	@Override
	public int hashCode() {
		return this.name.hashCode() + this.color.hashCode() + this.description.hashCode();
	}
}
