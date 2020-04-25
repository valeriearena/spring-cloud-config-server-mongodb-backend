package org.springframework.cloud.config.server.mongodb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.mongodb.environment.MongoEnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoEnvironmentRepositoryConfiguration {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public EnvironmentRepository environmentRepository() {
        return new MongoEnvironmentRepository(mongoTemplate);
    }

}
