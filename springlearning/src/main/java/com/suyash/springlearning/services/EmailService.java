package com.suyash.springlearning.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to , String subject , String body){

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
            log.info("Mail has succrefully send to {}" ,to);

        } catch (Exception e) {
            log.error("Error occured while Sending the mail" , e);
            throw new RuntimeException(e);
        }
    }

//    @KafkaListener(topics = "user-signup" , groupId = "email-group")
//    public void consume(String email){
//        sendMail(email ,"Succefully account has been created", "Thanks you for creating an account!");
//    }

    @KafkaListener(topics = "user-signup", groupId = "email-group")
    public void consume(String email) {

        if (email == null || !email.contains("@")) {
            log.warn("Invalid email received from Kafka: {}", email);
            return;
        }

        sendMail(email,
                "Account created successfully",
                "Thank you for creating an account!");
    }

}
