package com.banyan.settings.poc.configserver.controller;

import com.banyan.settings.poc.configserver.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.server.mongodb.environment.MongoDocument;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @PatchMapping
    public MongoDocument updateSetting(@RequestBody MongoDocument mongoDocument){
        return settingsService.updateSetting(mongoDocument);
    }

}
