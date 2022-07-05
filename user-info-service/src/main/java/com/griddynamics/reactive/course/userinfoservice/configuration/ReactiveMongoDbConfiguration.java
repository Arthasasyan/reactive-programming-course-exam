package com.griddynamics.reactive.course.userinfoservice.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;

@Configuration
public class ReactiveMongoDbConfiguration extends AbstractReactiveMongoConfiguration {

    @Value("${mongodb.connection.uri}")
    private String mongoDbConnectionUri;

    @Value("${mongodb.connection.database}")
    private String mongoDbDatabaseName;

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(mongoDbConnectionUri);
    }

    @Override
    protected String getDatabaseName() {
        return mongoDbDatabaseName;
    }
}
