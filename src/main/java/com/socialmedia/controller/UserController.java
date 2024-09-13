package com.socialmedia.controller;

import com.socialmedia.dto.UserLoginDTO;
import com.socialmedia.dto.UserSignUpDTO;
import org.springframework.util.StringUtils;

import com.socialmedia.service.JwtService;
import com.socialmedia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService signupNewUserService;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String UPLOAD_DIR = "/home/rapidsoft/Documents/connectyProfilePictures";

    @PostMapping("/signup")
    // @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> signUp(@ModelAttribute UserSignUpDTO userSignUpDTO,
                                    @RequestParam("file") MultipartFile file) throws IOException, ParseException {

        String filePath = saveProfilePic(file);
        userSignUpDTO.setProfilePic(filePath);

        ResponseEntity<?> savedUser = signupNewUserService.signUpNewUser(userSignUpDTO);

        System.out.println("Controler Hiiitttttttttttttt**************************************");
        return savedUser;
    }


    @PostMapping("/test")
    public ResponseEntity<?> test(){

        return new ResponseEntity<>("WORKING BHAI",HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginDTO userLoginDTO) {


        Authentication authenticate=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword()));

        if(authenticate.isAuthenticated()){

            String token= jwtService.generateToken(userLoginDTO.getUserName());

            return new ResponseEntity<>(token,HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed",HttpStatus.UNAUTHORIZED);
    }

    private String saveProfilePic(MultipartFile file) throws IOException {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Create the upload firectory if doesn't exist

        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Defining the path where the file will be saved
        String filePath = UPLOAD_DIR + File.separator + fileName;

        Path path = Paths.get(filePath);
        byte[] bytes = file.getBytes();
        Files.write(path, bytes);

        return filePath;

    }

}
