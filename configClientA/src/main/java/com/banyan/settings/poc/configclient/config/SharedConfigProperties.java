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
@ConfigurationProperties(prefix = "shared")
@RefreshScope
public class SharedConfigProperties {

    private String shared1;
    private String shared2;

    @PostConstruct
    public void logConstruct(){
        log.info("-------------- SharedConfigProperties CONSTRUCTED -------------- ");
    }

    @PreDestroy
    public void logDestroy(){
        log.info("-------------- SharedConfigProperties DESTROYED -------------- ");
    }

}
