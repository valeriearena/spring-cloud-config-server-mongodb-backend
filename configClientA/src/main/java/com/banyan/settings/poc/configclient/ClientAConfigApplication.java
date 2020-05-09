package com.banyan.settings.poc.configclient;

import com.banyan.settings.poc.configclient.config.ModuleProperties;
import com.banyan.settings.poc.configclient.config.SharedConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.scope.refresh.RefreshScopeRefreshedEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties({ModuleProperties.class, SharedConfigProperties.class})
public class ClientAConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientAConfigApplication.class, args);
	}

	@EventListener(RefreshScopeRefreshedEvent.class)
	public void onRefresh(RefreshScopeRefreshedEvent event) {
		log.info("-------------- RefreshScopeRefreshedEvent EVENT -------------: {}", event.toString());
	}

	@EventListener(EnvironmentChangeEvent.class)
	public void onRefresh(EnvironmentChangeEvent event) {
		log.info("-------------- EnvironmentChangeEvent EVENT -------------: {}", event.getKeys());
	}


}
