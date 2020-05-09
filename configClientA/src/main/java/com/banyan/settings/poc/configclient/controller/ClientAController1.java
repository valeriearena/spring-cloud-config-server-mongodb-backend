package com.banyan.settings.poc.configclient.controller;

import com.banyan.settings.poc.configclient.config.ModuleProperties;
import com.banyan.settings.poc.configclient.config.SharedConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/modulea1")
public class ClientAController1 {

	@Autowired
	private ModuleProperties moduleProperties;

	@Autowired
	private SharedConfigProperties sharedConfigProperties;

	@GetMapping
	public String getConf() {
		return String.format("setting1=%s <br/> shared1=%s", moduleProperties.getSetting1(), sharedConfigProperties.getShared1());

	}
}
