package com.banyan.settings.poc.configserver.service;

import com.banyan.settings.poc.configserver.webclient.RefreshWebClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.config.server.mongodb.environment.MongoDocument;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefreshService{

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RefreshWebClient refreshWebClient;

    @Async
    public void notifyModuleAToRefreshSystemSettings(MongoDocument mongoDocument){
        log.info("Refreshing module. module={}", mongoDocument.getModuleName());
        discoveryClient.getInstances(mongoDocument.getModuleName()).forEach((ServiceInstance s) -> {
            log.debug("SERVICEID={}", s.getServiceId());
            log.debug("URI={}", s.getUri().toString());
            log.debug(ToStringBuilder.reflectionToString(s));
            refreshWebClient.refresh(s.getUri().toString());
        });

    }
}
