package com.banyan.settings.poc.configclient.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Slf4j
@Component
public class ModuleASettingsTopicSubscriber {
    @Autowired
    private RefreshEndpoint refreshEndpoint;

    @JmsListener(destination = "${modulea.settings.jms.topic.name}", containerFactory = "${modulea.settings.jms.topic.connection.factory}", subscription = "${modulea.settings.jms.topic.subscription}")
    public void receiveMessage(TextMessage message){
        refreshEndpoint.refresh();
        log.info("Module A consuming JMS message notification to update settings.");

    }
}
