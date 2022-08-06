package com.user.service;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.user.dto.UserDto;
import com.user.entity.User;
import com.user.repository.UserRepository;
import com.user.utils.CommonServiceHttpMessage;
import com.user.utils.ResponseModel;
import com.user.utils.ResponseStatus;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto getLatestUser() {

		User user = this.userRepository.findFirstByOrderByDateAndTimeDesc();
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
	}

	@Override
	public ResponseModel createNewUser(UserDto userDto) {
		ResponseModel rs;
		User user = new User();

		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmailAddress(userDto.getEmailAddress());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setDateAndTime(new Date());
		rs = ResponseStatus.create(CommonServiceHttpMessage.USER_CREATED, userRepository.save(user), HttpStatus.OK,
				HttpStatus.OK.value());

		return rs;
	}
}
