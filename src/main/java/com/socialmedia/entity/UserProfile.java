package com.socialmedia.entity;


import com.socialmedia.enums.GenderEnum;

// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GenerationType;



@Entity
@javax.persistence.Table(name = "user_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    private String emailId;

    private String userName;


    private String password;

    private String country;


    private Date dob;

    //Give the path for profile pic
    private String profilePic;


    private GenderEnum gender;


}
