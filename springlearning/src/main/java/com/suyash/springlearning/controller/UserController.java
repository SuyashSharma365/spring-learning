package com.suyash.springlearning.controller;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("users")
public class UserController {

    //create service object
    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity){

        try{
            userService.saveEntry(userEntity);
            return new ResponseEntity<>(userEntity , HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String userName){
        Optional<UserEntity>  userEntity = Optional.ofNullable(userService.findByUserName(userName));
        if(userEntity.isPresent()){
            return new ResponseEntity<>(userEntity.get() , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userName}")
    public ResponseEntity<?> deleteUserById(@PathVariable String userName){
        UserEntity user = userService.findByUserName(userName);

        if(user != null){
            userService.deleteById(user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody UserEntity newUser){

        UserEntity oldUser = userService.findByUserName(userName);

        if(oldUser != null){

            if(newUser.getUserName() != null && !newUser.getUserName().isEmpty()){
                oldUser.setUserName(newUser.getUserName());
            }

            if(newUser.getPassword() != null && !newUser.getPassword().isEmpty()){
                oldUser.setPassword(newUser.getPassword());
            }

            userService.saveEntry(oldUser);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
