package com.suyash.springlearning.controller;


import com.suyash.springlearning.cache.AppCache;
import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("all-users")
    public ResponseEntity<?> getAllUser(){
        List<UserEntity> users = userService.getAllUsers();
        if(!users.isEmpty()){
            return new ResponseEntity<>(users , HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Autowired
    AppCache appCache;

    @GetMapping("clear-app-cache")
    public void clearappcache(){
        log.info("APP cache is initialized");
        appCache.init();
    }
}
