package com.suyash.springlearning.services;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class UserService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveEntry(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if(userEntity.getRoles() == null){
            userEntity.setRoles(Arrays.asList("USER"));
        }
        userEntryRepository.save(userEntity);
    }

    public UserEntity findById(ObjectId id){
        return userEntryRepository.findById(id).orElse(null);
    }

    public void deleteById(ObjectId id){
        userEntryRepository.deleteById(id);
    }

    public UserEntity findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }

}
