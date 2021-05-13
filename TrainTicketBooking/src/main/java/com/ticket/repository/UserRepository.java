/**
 * 
 */
package com.ticket.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ticket.dto.UserDto;
import com.ticket.dto.UserResponseDto;
import com.ticket.model.User;

/**
 * @author SUDESHA
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * @param page
	 * @return
	 */
	@Query("select new com.ticket.dto.UserResponseDto(userId,userName,password,age,emailId,phoneNo,role) from User")
	List<UserResponseDto> getAllUsers(Pageable page);

	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	@Query("select new com.ticket.dto.UserDto(userName,password) from User u where u.userName=:userName and u.password=:password")
	UserDto getByUserNameAndPassword(@Param("userName") String userName, @Param("password") String password);

}
