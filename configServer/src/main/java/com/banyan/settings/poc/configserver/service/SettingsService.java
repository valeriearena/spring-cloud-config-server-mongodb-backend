package com.banyan.settings.poc.configserver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.mongodb.environment.MongoDocument;
import org.springframework.cloud.config.server.mongodb.environment.MongoEnvironmentRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SettingsService {

    @Autowired
    private MongoEnvironmentRepository mongoEnvironmentRepository;

    @Autowired
    private RefreshService refreshService;

    public MongoDocument updateSetting(MongoDocument mongoDocument){
        MongoDocument document = mongoEnvironmentRepository.updateSetting(mongoDocument);
        refreshService.notifyModuleAToRefreshSystemSettings(mongoDocument);
        log.info("Updated setting. key={}, value={}", mongoDocument.getKey(), mongoDocument.getValue());
        return document;
    }


}
