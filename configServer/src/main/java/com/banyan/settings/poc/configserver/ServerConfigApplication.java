package com.banyan.settings.poc.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.mongodb.EnableMongoConfigServer;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableMongoConfigServer
@EnableDiscoveryClient
@EnableAsync
public class ServerConfigApplication{

	public static void main(String[] args) {
		SpringApplication.run(ServerConfigApplication.class, args);
	}

}
