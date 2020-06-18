package it.uniroma3.siw.ProgettoSIWGIUGNO2020.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 100)
	private String firstname;

	@Column(nullable = false, length = 100)
	private String lastname;

	@Column(nullable = false, updatable = false)
	private LocalDate creationDate;

	@OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
	private List<Project> ownedProjects;

	@ManyToMany(mappedBy = "members")
	private List<Project> visibleProjects;

	public User() {
		this.ownedProjects = new ArrayList<>();
		this.visibleProjects = new ArrayList<>();
	}

	public User(String firstname, String lastname) {
		this();
		this.firstname = firstname;
		this.lastname = lastname;
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

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public void setOwnedProjects(List<Project> ownedProjects) {
		this.ownedProjects = ownedProjects;
	}

	public List<Project> getOwnedProjects(){
		return this.ownedProjects;
	}

	public void setVisibleProjects(List<Project> visibleProjects) {
		this.visibleProjects = visibleProjects;
	}

	public List<Project> getVisibleProjects(){
		return this.visibleProjects;
	}

	public void addOwnedProject(Project project) {
		this.ownedProjects.add(project);
	}

	public void addVisibleProject(Project project) {
		this.visibleProjects.add(project);
	}

	@Override
	public String toString() {
		return "User [firstName=" + this.firstname + ", lastName=" + this.lastname + ", "
				+ "creationTime=" + this.creationDate.toString();
	}

	@Override
	public boolean equals(Object o) {
		User that = (User) o;
		return this.firstname.equals(that.firstname) && this.lastname.equals(that.lastname) &&
				this.creationDate.equals(that.creationDate);
	}

	@Override
	public int hashCode() {
		return this.firstname.hashCode() + this.lastname.hashCode() + this.creationDate.hashCode();
	}

}
