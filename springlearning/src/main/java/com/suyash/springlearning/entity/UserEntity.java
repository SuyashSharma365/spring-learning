package com.suyash.springlearning.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "userEntry")
public class UserEntity {
    @Id
    private String id;

    private String name;

    private Date dateOfBirth;

    public UserEntity(){
    }

    public UserEntity(String id , String name , Date dateofBirth){
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateofBirth;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
