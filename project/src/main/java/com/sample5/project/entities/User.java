package com.sample5.project.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
public class User {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message="User can not be empty")
	@Size(min=2, max=20 ,message="user name must be between 3 to 12 charactor")
	private String name;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email should be valid")
	@Column(unique=true)
	private String email;
	
	
	@NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;
				
	private String role;
	
	private boolean enable;
	
	private String imagURL;
	@Column(length=500)
	private String about;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
    private List<Contact> contact=new ArrayList<>();


	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public User(int id, String name, String email, String password, String role, boolean enable, String imagURL,
			String about, List<Contact> contact) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.enable = enable;
		this.imagURL = imagURL;
		this.about = about;
		this.contact = contact;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public boolean isEnable() {
		return enable;
	}


	public void setEnable(boolean enable) {
		this.enable = enable;
	}


	public String getImagURL() {
		return imagURL;
	}


	public void setImagURL(String imagURL) {
		this.imagURL = imagURL;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public List<Contact> getContact() {
		return contact;
	}


	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
				+ ", enable=" + enable + ", imagURL=" + imagURL + ", about=" + about + ", contact=" + contact + "]";
	}
	
	
}
