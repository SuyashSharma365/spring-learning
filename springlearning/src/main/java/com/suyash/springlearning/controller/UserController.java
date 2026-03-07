package com.suyash.springlearning.controller;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("userEntry")
public class UserController {

    //create service object
    @Autowired
    UserService userService;

    @PostMapping("/")
    public boolean createUser(@RequestBody UserEntity userEntity){
        userService.saveEntry(userEntity);
        return true;
    }

    @GetMapping("/{userId}")
    public UserEntity getUser(@PathVariable String userId){
        return userService.findById(userId);
    }

    @DeleteMapping("/{userId}")
    public boolean deleteUserById(@PathVariable String userId){
        userService.deleteById(userId);
        return true;
    }

    @PutMapping("/{userId}")
    public boolean updateUser(@PathVariable String userId , @RequestBody UserEntity newUser){
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
            return true;

        }
        return false;
    }

}
