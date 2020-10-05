package net.app.fixMypLACE.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	@NotBlank(message = "Please Enter Email Address!")
	@NotNull(message = "Email Requires Valid Value")
	@NotEmpty(message = "Email Can Not Be Empty")
	@Email(message = "Please Enter a Valid  Email Format")
	private String email;

	@Column(name = "password")
	@Length(min = 8, message = "Your Password Must Have Atleast 8 Characters")
	@NotEmpty(message = "Please Enter Your password")
	@org.springframework.data.annotation.Transient
	private String password;

	@Column(name = "first_name")
	@NotEmpty(message = "Please Enter Your First Name")
	@Size(min = 2, max = 50, message = "First Name Must Atleast Have Two Characters")
	@Pattern(regexp = "[A-Za-z. ]*", message = "First Name Requires Alphaberts Only")
	private String first_name;

	@Column(name = "last_name")
	@NotEmpty(message = "Please Enter Your Last Name")
	@Size(min = 2, max = 50, message = "Last Name Must Have Atleast Two Characters")
	@Pattern(regexp = "[A-Za-z. ]*", message = "Last Name Requires Alphaberts Only")
	private String lastName;

	@Size(min = 10, max = 12, message = "Contact Number Must Atleast be 10")
	@Pattern(regexp = "[0-9.\\-+ ]*", message = "contact number requires only numbers")
	@NotBlank(message = "Please enter contact number!")
	@Column(name = "contact_number")
	private String contactNumber;

	@Size(min = 2, max = 50, message = "Username Requires Atleast Two Characters")
	@Pattern(regexp = "[A-Za-z. ]*", message = "Username Requires Alphabets Only")
	private String username;

	// User Address
	@Size(min = 2, max = 50, message = "Line One Requires Atleast Two Characters")
	@NotBlank(message = "Please Enter Address Line One")
	@NotEmpty(message = "Address Line One Can Not Be Empty")
	@Column(name = "address_line_one")
	private String addressLineOne;

	@Size(min = 2, max = 50, message = "Line Two Requires Atleast Two Characters")
	@NotBlank(message = "Please Enter Address Line Two!")
	@NotEmpty(message = "Address Line Two Can Not Be Empty")
	@Column(name = "address_line_two")
	private String addressLineTwo;

	@Size(min = 2, max = 50, message = "City Requires Atleast Two Characters")
	@NotEmpty(message = "City Can Not Be Empty")
	@NotBlank(message = "Please enter City!")
	private String city;

	@Pattern(regexp = "[0-9.\\-+ ]*", message = "Postal Code requires valid numaric characters")
	@Column(name = "postal_code")
	@NotBlank(message = "Please enter Postal Code!")
	@Size(min = 3, max = 4, message = "Postal Code Requires Atleast 3 Numbers")
	@NotEmpty(message = "Postal Code Can Not Be Empty")
	private String postalCode;

	@Column(name = "active")
	private boolean active;

	private String profilePicture = "none";
	private String lastLogin;
	private String token;

	@OneToMany()
	private Set<Event> userDocuments = new HashSet<Event>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Collection<Role> roles;

	// @OneToMany(mappedBy = "user")
	// private Collection<Post> posts;
	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	//
	// public Collection<Post> getPosts() {
	// return posts;
	// }
	//
	// public void setPosts(Collection<Post> posts) {
	// this.posts = posts;
	// }\\

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Set<Event> getUserDocuments() {
		return userDocuments;
	}

	public void setUserDocuments(Set<Event> userDocuments) {
		this.userDocuments = userDocuments;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
