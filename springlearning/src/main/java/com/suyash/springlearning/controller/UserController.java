package com.suyash.springlearning.controller;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("userEntry")
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

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String userId){
        Optional<UserEntity>  userEntity = Optional.ofNullable(userService.findById(userId));
        if(userEntity.isPresent()){
            return new ResponseEntity<>(userEntity.get() , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable String userId){
        UserEntity user = userService.findById(userId);

        if(user != null){
            userService.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId , @RequestBody UserEntity newUser){
        UserEntity oldUser = userService.findById(userId);
        if(oldUser != null){

            oldUser.setName(
                    newUser.getName() != null && !newUser.getName().equals("")
                            ? newUser.getName()
                            : oldUser.getName()
            );

            oldUser.setDateOfBirth(
                    newUser.getDateOfBirth() != null
                            ? newUser.getDateOfBirth()
                            : oldUser.getDateOfBirth()
            );
            userService.saveEntry(oldUser);
            return new ResponseEntity<>(HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
