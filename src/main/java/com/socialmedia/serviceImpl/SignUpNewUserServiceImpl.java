package com.socialmedia.serviceImpl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.socialmedia.dto.UserLoginDTO;
import com.socialmedia.dto.UserSignUpDTO;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.enums.GenderEnum;
import com.socialmedia.repository.UserRepository;
import com.socialmedia.service.UserService;

@Service
public class SignUpNewUserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> signUpNewUser(UserSignUpDTO userSignUpDTO) throws ParseException {


        UserProfile userProfile=new UserProfile();
        userProfile.setEmailId(userSignUpDTO.getEmail());
        userProfile.setPassword( passwordEncoder.encode(userSignUpDTO.getPassword()));
        userProfile.setCountry(userSignUpDTO.getCountry());
        userProfile.setProfilePic(userSignUpDTO.getProfilePic());
        userProfile.setProfilePic(userSignUpDTO.getProfilePic());
        userProfile.setFullName(userSignUpDTO.getFullName());
        userProfile.setUserName(userSignUpDTO.getUserName());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        Date dob=simpleDateFormat.parse(userSignUpDTO.getDob());
        userProfile.setDob(dob);
        if(userSignUpDTO.getGender().equalsIgnoreCase(GenderEnum.MALE.name()))
        {
            userProfile.setGender(GenderEnum.MALE);
        }
        else if(userSignUpDTO.getGender().equalsIgnoreCase(GenderEnum.FEMALE.name())){
            userProfile.setGender(GenderEnum.FEMALE);
        }
        else {
            userProfile.setGender(GenderEnum.OTHERS);
        }

        UserProfile userProfileSavedDB= userRepository.save(userProfile);

        return new ResponseEntity<>(userProfileSavedDB, HttpStatus.OK);
    }



    @Override
    public ResponseEntity<?> login(UserLoginDTO userLoginDTO) {
        return null;
    }


}


