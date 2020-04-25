package com.banyan.settings.poc.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.mongodb.EnableMongoConfigServer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableMongoConfigServer
public class ServerConfigApplication{

	public static void main(String[] args) {
		SpringApplication.run(ServerConfigApplication.class, args);
	}

	@Bean
	public HttpTraceRepository httpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}

}
