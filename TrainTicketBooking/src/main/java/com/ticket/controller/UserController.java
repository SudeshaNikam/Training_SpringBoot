/**
 * 
 */
package com.ticket.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ticket.dto.UserDto;
import com.ticket.dto.UserRequestDto;
import com.ticket.dto.UserResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.service.UserService;

/**
 * @author SUDESHA
 *
 */

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	@Autowired
	UserService userService;

	static Logger logger = Logger.getLogger(UserController.class);

	/**
	 * 
	 * @param userResponseDto
	 * @return
	 * @throws Exception
	 */
	@PostMapping(" ")
	public ResponseEntity<String> SaveUser(@RequestBody @Valid UserRequestDto userResponseDto)
			throws RecordAlreadyExist {

		userService.saveUser(userResponseDto);
		logger.info("Saved User Details");
		return new ResponseEntity<String>("Successfully Saved User Details", HttpStatus.CREATED);

	}

	/**
	 * 
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(" ")
	public List<UserResponseDto> getAllUser(@RequestParam int pageNumber, @RequestParam int pageSize)
			throws RecordNotFoundException {
		logger.info("view all User Details");
		return userService.getAllUsers(pageNumber, pageSize);
	}

	/**
	 * 
	 * @param userId
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping("/{userId}")
	public UserResponseDto getUserById(@PathVariable Long userId) throws RecordNotFoundException {
		logger.info("view  User Details by user id");
		return userService.getUsersById(userId);
	}

	/**
	 * 
	 * @param userId
	 * @param userResponseDto
	 * @return
	 * @throws RecordNotFoundException
	 */
	@PutMapping("/{userId}")
	public ResponseEntity<String> updateUser(@PathVariable Long userId,
			@RequestBody @Valid UserRequestDto userResponseDto) throws RecordNotFoundException {
		userService.updateUser(userId, userResponseDto);
		logger.info("update User Details");
		return new ResponseEntity<String>("Successfully Update User Details", HttpStatus.CREATED);

	}

	/**
	 * 
	 * @param userId
	 * @throws RecordNotFoundException
	 */

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) throws RecordNotFoundException {
		logger.info("delete user details successfully");
		userService.deleteUser(userId);

	}

	/**
	 * 
	 * @param user
	 * @param result
	 * @return
	 */
	@GetMapping("/login")
	public ResponseEntity<String> loginSuccess(@Valid UserDto user) {
		try {

			UserDto userlogin = userService.getByUserNameAndPassword(user.getUserName(), user.getPassword());
			if (ObjectUtils.isNotEmpty(userlogin)) {
				logger.info("login successfully");
				return new ResponseEntity<String>("You Are Successfully logged in ", HttpStatus.OK);
			} else {
				logger.error("Wrong UserId or Password");
				return new ResponseEntity<String>("Wrong UserId or Password", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Error while login user : " + e.getMessage(), e);
			return new ResponseEntity<String>("Error while login user", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
