package com.banyan.settings.poc.configserver.service;

import com.banyan.settings.poc.configserver.jms.TopicProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.config.server.mongodb.environment.MongoDocument;
import org.springframework.cloud.config.server.mongodb.environment.MongoEnvironmentRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private MongoEnvironmentRepository mongoEnvironmentRepository;

    @Autowired
    private TopicProducer topicProducer;

    @Autowired
    private DiscoveryClient discoveryClient;

    public MongoDocument updateSetting(MongoDocument mongoDocument){

        MongoDocument document = mongoEnvironmentRepository.updateSetting(mongoDocument);
        topicProducer.notifyModuleAToRefreshSystemSettings(mongoDocument.getModuleName());
        return document;

    }
}
