/**
 * 
 */
package com.ticket.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ticket.dto.UserDto;
import com.ticket.dto.UserRequestDto;
import com.ticket.dto.UserResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;

/**
 * @author SUDESHA
 *
 */

@Service
public interface UserService {

	/**
	 * @param userResponseDto
	 * @throws UserAlreadyExist
	 * @throws RecordAlreadyExist
	 * @throws Exception
	 */
	void saveUser(@Valid UserRequestDto userResponseDto) throws RecordAlreadyExist;

	/**
	 * @param userId
	 * @param user
	 * @throws RecordNotFoundException
	 */
	void updateUser(Long userId, UserRequestDto user) throws RecordNotFoundException;

	/**
	 * @param userId
	 * @throws RecordNotFoundException
	 * @throws RecordNotFoundException
	 */
	void deleteUser(Long userId) throws RecordNotFoundException;

	/**
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<UserResponseDto> getAllUsers(int pageNumber, int pageSize) throws RecordNotFoundException;

	/**
	 * @param userId
	 * @return
	 * @throws RecordNotFoundException
	 */
	UserResponseDto getUsersById(Long userId) throws RecordNotFoundException;

	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	UserDto getByUserNameAndPassword(String userName, String password);

	/**
	 * @return
	 * @throws RecordNotFoundException
	 */
	List<UserResponseDto> getAllUserDetails() throws RecordNotFoundException;

}
