package com.banyan.settings.poc.configserver.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * WebClient uses the Reactive Streams API. WebClient has replaced RestTemplate, which is deprecated as of Spring 4.3.
 *
 * Although WebClient supports asynchronous REST calls, the WebClient in ModuleARestClient
 * is used in a synchronous manner and blocks until it receives a response.
 */
@Slf4j
@Component
public class RefreshWebClient {

    private WebClient webClient;

    private static final String REFRESH_ENDPOINT = "/actuator/refresh";

    public void refresh(String uri) {

        log.info("Spring Cloud Config sending a request to refresh module. uri={}", uri);
        webClient = WebClient
                .builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.newConnection().compress(true).wiretap(true)))
                .exchangeStrategies( // Explicitly enable header logging. By default, headers are masked.
                        ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .baseUrl(uri + REFRESH_ENDPOINT)
                .build();

        webClient.post().retrieve().bodyToMono(Void.class).block();
        log.info("Spring Cloud Config sent request to refresh module. uri={}", uri);
    }

}
