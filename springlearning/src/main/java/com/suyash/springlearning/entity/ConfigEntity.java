package com.suyash.springlearning.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "configurations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigEntity {

    private String key;

    private String value;
}
