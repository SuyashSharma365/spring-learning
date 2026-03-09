package com.suyash.springlearning.services;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserEntryRepository userEntryRepository;


    public void saveEntry(UserEntity userEntity){
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
