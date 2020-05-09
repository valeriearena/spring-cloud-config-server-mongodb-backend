package com.banyan.settings.poc.configclient.controller;

import com.banyan.settings.poc.configclient.config.ModuleProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/modulea2")
public class ClientAController2 {

    @Autowired
    private ModuleProperties moduleProperties;

    @GetMapping
    public String getConf() {

        return String.format("setting2=%s", moduleProperties.getSetting2());

    }

}
