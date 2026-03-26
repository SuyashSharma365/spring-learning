package com.suyash.springlearning.Tests;


import com.suyash.springlearning.entity.UserEntity;
import com.suyash.springlearning.services.EmailService;
import com.suyash.springlearning.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void testNewUser() {

        UserEntity user = new UserEntity();
        user.setUserName("suyash");
        user.setPassword("1234");

        userService.saveNewEntry(user);

        UserEntity savedUser = userService.findByUserName("suyash");

        assertNotNull(savedUser);
        assertEquals("suyash", savedUser.getUserName());
    }

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMail(){
        emailService.sendMail("suyash3111@gmail.com",
                "Testing",
                "This is testing mail");
    }


}
