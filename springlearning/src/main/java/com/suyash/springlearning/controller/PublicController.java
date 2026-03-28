package com.suyash.springlearning.controller;


import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    private KafkaTemplate<String , String> kafkaTemplate;

    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){

        try{
            userService.saveNewEntry(userEntity);
            kafkaTemplate.send("user-signup" , userEntity.getEmail());
            return new ResponseEntity<>(userEntity , HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
