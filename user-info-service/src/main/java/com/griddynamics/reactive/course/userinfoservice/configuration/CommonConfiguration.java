package com.griddynamics.reactive.course.userinfoservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    public ObjectMapper standardMapper() {
        return new ObjectMapper();
    }
}
