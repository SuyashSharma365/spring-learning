package com.suyash.springlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement
@SpringBootApplication
public class SpringlearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringlearningApplication.class, args);
	}

    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }


}
