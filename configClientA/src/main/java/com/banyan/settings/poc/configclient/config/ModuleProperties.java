package com.banyan.settings.poc.configclient.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "clienta")
@RefreshScope
public class ModuleProperties {

    private String setting1;
    private String setting2;
    private int test;

    @PostConstruct
    public void logConstruct(){

        log.info("-------------- ModuleProperties CONSTRUCTED -------------- ");
    }

    @PreDestroy
    public void logDestroy(){
        log.info("-------------- ModuleProperties DESTROYED -------------- ");
    }
}
