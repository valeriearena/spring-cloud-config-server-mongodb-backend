package com.banyan.settings.poc.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/modulea")
@RefreshScope
public class ClientAController {

	@Value("${setting1}")
	private String setting1;

	@Value("${setting2}")
	private String setting2;

	@Value("${shared1}")
	private String shared1;

	@Value("${shared2}")
	private String shared2;

	@GetMapping
	public String getConf() {

		return String.format("setting1=%s <br/> setting2=%s <br/> shared1=%s <br/> shared2=%s", setting1, setting2, shared1, shared2);

	}
}
