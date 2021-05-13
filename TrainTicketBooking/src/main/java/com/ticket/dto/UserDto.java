/**
 * 
 */
package com.ticket.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
public class UserDto {

	@NotBlank(message = "userName is mandatory")
	@Size(max = 150, message = "User Name length should be less than or equal to 150")
	private String userName;

	@Size(min = 6, message = "password length should be greater than 6")
	@NotBlank(message = "password is mandatory")
	private String password;
}
