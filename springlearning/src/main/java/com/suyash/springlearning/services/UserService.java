package com.suyash.springlearning.services;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.repository.UserEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class UserService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public void saveNewEntry(UserEntity userEntity){

        try {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            if (userEntity.getRoles() == null) {
                userEntity.setRoles(Arrays.asList("USER"));
            }
            userEntryRepository.save(userEntity);
            log.info("User {} is Created" , userEntity.getUserName());
        } catch (Exception e) {
            log.error("Error occurred while saving user: {}", userEntity.getUserName(), e);
            throw new RuntimeException(e);
        }

    }

    public void saveOldEntry(UserEntity userEntity){
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

    public void deleteByUserName(String username){
        userEntryRepository.deleteByUserName(username);
    }

    public List<UserEntity> getAllUsers(){
        return userEntryRepository.findAll();
    }


}
