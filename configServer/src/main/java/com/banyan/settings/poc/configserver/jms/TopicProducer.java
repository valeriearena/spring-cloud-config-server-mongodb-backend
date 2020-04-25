package com.banyan.settings.poc.configserver.jms;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopicProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final String TOPIC_SUFFIX = "-settings-topic";

    public void notifyModuleAToRefreshSystemSettings(String moduleName){
        String topicName = moduleName.toLowerCase() + TOPIC_SUFFIX;
        String jsonMessage = new Gson().toJson("refresh");
        log.info("Publishing JMS message to topic {} to refresh settings: {}", topicName, jsonMessage);
        jmsTemplate.convertAndSend(topicName, jsonMessage);
    }
}
