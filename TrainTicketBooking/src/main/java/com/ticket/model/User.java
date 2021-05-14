/**
 * 
 */
package com.ticket.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ticket.constants.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SUDESHA
 *
 */

@Entity
@Table(name = "USER")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@Column(name = "ID", length = 64, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@NotBlank(message = "userName is mandatory")
	@Size(max = 150, message = "User Name length should be less than or equal to 150")
	@Column(name = "USER_NAME", nullable = false, length = 150)
	private String userName;

	@Size(min = 6, message = "password length should be greater than 6")
	@NotBlank(message = "password is mandatory")
	@Column(name = "PASSWORD", nullable = false, length = 150)
	private String password;

	@Pattern(regexp = "[0-9]+", message = "age should be valid")
	@Column(name = "AGE")
	private String age;

	@NotBlank(message = "emailId is mandatory")
	@Email
	@Column(name = "EMAILID", nullable = false, length = 100, unique = true)
	private String emailId;

	@Size(max = 10, message = "Phone Number length should be equal to 10")
	@Pattern(regexp = "(^\\d{10}$)")
	@Column(name = "PHONE_NO", length = 10, unique = true)
	private String phoneNo;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", nullable = false)
	private Role role;

}
