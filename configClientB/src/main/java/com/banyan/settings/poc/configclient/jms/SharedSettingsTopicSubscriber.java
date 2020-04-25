package com.banyan.settings.poc.configclient.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.TextMessage;

@Slf4j
@Component
public class SharedSettingsTopicSubscriber {

    @Autowired


    private RefreshEndpoint refreshEndpoint;

    @JmsListener(destination = "${moduleb.system.settings.jms.topic.name}", containerFactory = "${moduleb.settings.jms.topic.connection.factory}", subscription = "${moduleb.system.settings.jms.topic.subscription}")
    public void receiveMessage(TextMessage message){
        refreshEndpoint.refresh();
        log.info("Module B consuming JMS message notification to update settings.");
    }

}
