package com.griddynamics.reactive.course.userinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class UserInfoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserInfoServiceApplication.class, args);
    }

}
