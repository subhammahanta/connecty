package com.socialmedia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestingController {

    @GetMapping("/block/{id}")
    public ResponseEntity<?> testme(@PathVariable Integer id){

        return  new ResponseEntity<>(id, HttpStatus.OK);

    }

}
