/**
 * 
 */
package com.ticket.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ticket.dto.UserDto;
import com.ticket.dto.UserRequestDto;
import com.ticket.dto.UserResponseDto;
import com.ticket.exception.RecordAlreadyExist;
import com.ticket.exception.RecordNotFoundException;
import com.ticket.model.User;
import com.ticket.repository.UserRepository;
import com.ticket.service.UserService;

/**
 * @author SUDESHA
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	static Logger logger = Logger.getLogger(TrainServiceImpl.class);

	@Override
	public void saveUser(UserRequestDto userResponseDto) throws RecordAlreadyExist {
		try {
			User user = new User();
			BeanUtils.copyProperties(userResponseDto, user);
			userRepository.save(user);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			logger.error("Email Id Already Exist ..");
			throw new RecordAlreadyExist("Email Id Already Exist ..");
		}
	}

	@Override
	public void updateUser(Long userId, UserRequestDto userResponseDto) throws RecordNotFoundException {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			user.setAge(userResponseDto.getAge());
			user.setEmailId(userResponseDto.getEmailId());
			user.setPassword(userResponseDto.getPassword());
			user.setPhoneNo(userResponseDto.getPhoneNo());
			user.setUserName(userResponseDto.getUserName());
			user.setRole(userResponseDto.getRole());
			userRepository.save(user);
		} else {
			logger.error("Invalid User id : " + userId);
			throw new RecordNotFoundException("Invalid User id : " + userId);
		}

	}

	@Override
	public void deleteUser(Long userId) throws RecordNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			userRepository.deleteById(userId);
		} else {
			logger.error("Invalid User id : " + userId);
			throw new RecordNotFoundException("Invalid User id : " + userId);
		}

	}

	@Override
	public UserResponseDto getUsersById(Long userId) throws RecordNotFoundException {
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			UserResponseDto userResponseDto = new UserResponseDto();
			BeanUtils.copyProperties(user, userResponseDto);
			return userResponseDto;
		} else {
			logger.error("Invalid User id : " + userId);
			throw new RecordNotFoundException("Invalid User id : " + userId);
		}

	}

	@Override
	public List<UserResponseDto> getAllUsers(int pageNumber, int pageSize) throws RecordNotFoundException {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<User> userList = userRepository.findAll(page).getContent();
		List<UserResponseDto> userResponseDtos = new ArrayList<UserResponseDto>();
		BeanUtils.copyProperties(userList, userResponseDtos);
		if (ObjectUtils.isNotEmpty(userList)) {
			userList.forEach(user -> {
				UserResponseDto userDto = new UserResponseDto();
				BeanUtils.copyProperties(user, userDto);
				userResponseDtos.add(userDto);
			});
		} else {
			logger.error("NO any data Found");
			throw new RecordNotFoundException("NO any data Found");
		}
		return userResponseDtos;

	}

	@Override
	public UserDto getByUserNameAndPassword(String userName, String password) {
		return userRepository.getByUserNameAndPassword(userName, password);
	}

	@Override
	public List<UserResponseDto> getAllUserDetails() throws RecordNotFoundException {
		List<User> userList = userRepository.findAll();
		List<UserResponseDto> userResponseDtos = new ArrayList<UserResponseDto>();
		BeanUtils.copyProperties(userList, userResponseDtos);
		if (ObjectUtils.isNotEmpty(userList)) {
			userList.forEach(user -> {
				UserResponseDto userDto = new UserResponseDto();
				BeanUtils.copyProperties(user, userDto);
				userResponseDtos.add(userDto);
			});
		} else {
			logger.error("NO any data Found");
			throw new RecordNotFoundException("NO any data Found");
		}
		return userResponseDtos;

	}

}
