package com.suyash.springlearning.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blogs_entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity {

    @Id
    private ObjectId id;

    @NonNull
    private String title;

    private String content;
}
