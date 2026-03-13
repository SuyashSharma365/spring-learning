package com.suyash.springlearning.services;

import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        UserEntity user = userEntryRepository.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder().username(user.getUserName()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
    }

}
