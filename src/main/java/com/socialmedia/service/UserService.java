package com.socialmedia.service;

import com.socialmedia.dto.UserLoginDTO;
import com.socialmedia.dto.UserSignUpDTO;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface UserService {

    ResponseEntity<?> signUpNewUser(UserSignUpDTO userSignUpDTO) throws ParseException;

    ResponseEntity<?>login(UserLoginDTO userLoginDTO);
}
