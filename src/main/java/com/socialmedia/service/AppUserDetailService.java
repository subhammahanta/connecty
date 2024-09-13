package com.socialmedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.repository.UserRepository;

@Service
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserProfile userProfile;
        Optional<UserProfile> userProfileOptional=null;

        if(username.contains("@")){
            //It means user wants to login with email id

            userProfileOptional=   userRepository.findByEmailId(username);

            if(userProfileOptional.isPresent()){
                userProfile=userProfileOptional.get();
            }
            else{

                throw new UsernameNotFoundException("User not Found with this mail id");

            }


        }
        else {
            //It means user wants to login with userName

            userProfileOptional=   userRepository.findByUserName(username);

            if(userProfileOptional.isPresent()){
                userProfile=userProfileOptional.get();
            }
            else{
                throw new UsernameNotFoundException("User not Found with this user name");
            }
        }


        return userProfileOptional.map(AppUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found in the system"));

    }

}
