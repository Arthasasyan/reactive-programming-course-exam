package com.griddynamics.reactive.course.userinfoservice.repository;

import com.griddynamics.reactive.course.userinfoservice.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
