package it.uniroma3.siw.ProgettoSIWGIUGNO2020.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;


@Entity
public class Credentials {

	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, length = 10)
	private String role;

	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	@Column(nullable = false, updatable = false)
	private LocalDate creationDate;

	public Credentials() {

	}

	public Credentials(String username, String password) {
		this();
		this.username = username;
		this.password = password;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return this.role;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return this.user;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	@Override
	public boolean equals(Object o) {
		Credentials that = (Credentials) o;
		return this.username.equals(that.username) && this.password.equals(that.password) &&
				this.role.equals(that.role) && this.creationDate.equals(that.creationDate);
	}

	@Override
	public int hashCode() {
		return this.username.hashCode() + this.password.hashCode() + this.role.hashCode()
		+ this.creationDate.hashCode();
	}

	@Override
	public String toString() {
		return "Credentials{" +
				"id=" + this.id +
				", username='" + this.username + '\'' +
				", role='" + this.role + '\'' +
				", creationDate=" + this.creationDate +
				'}';
	}
}
