/**
 * 
 */
package com.ticket.dto;

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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	@NotBlank(message = "userName is mandatory")
	@Size(max = 150, message = "User Name length should be less than or equal to 150")
	private String userName;

	@Size(min = 6, message = "password length should be greater than 6")
	@NotBlank(message = "password is mandatory")
	private String password;

	@Pattern(regexp = "[0-9]+", message = "age should be valid")
	private String age;

	@NotBlank(message = "emailId is mandatory")
	@Email
	private String emailId;

	@Size(max = 10, message = "Phone Number length should be equal to 10")
	@Pattern(regexp = "(^\\d{10}$)")
	private String phoneNo;

	private Role role;

}
