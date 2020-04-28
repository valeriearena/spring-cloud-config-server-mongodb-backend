package com.banyan.settings.poc.configserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URI;
import java.util.*;

@Slf4j
@Configuration
@Profile("local")
public class LocalConfig {

    @Value("${configserver.refresh.endpoint.module.list}")
    private String refreshEndpointModuleList;

    @Bean
    public DiscoveryClient discoveryClient(Map<String,String> moduleMap){
        return new LocalDiscoveryClient(moduleMap);
    }

    @Bean
    public Map<String,String> moduleMap(){
        Map<String,String> map = new HashMap<>();
        List<String> refreshModuleList = Arrays.asList(refreshEndpointModuleList.split(","));
        refreshModuleList.forEach(m -> {
            String[] moduleArray = m.split("=");
            map.put(moduleArray[0], moduleArray[1]);
        });
        return map;
    }

    private class LocalDiscoveryClient implements DiscoveryClient {
        private Map<String, List<ServiceInstance>> serviceInstanceMap = new HashMap<>();

        public LocalDiscoveryClient(Map<String, String> moduleMap) {
            List<ServiceInstance> serviceInstances = null;
            ServiceInstance serviceInstance = null;
            for (String key : moduleMap.keySet()) {
                serviceInstances = new ArrayList<>();
                serviceInstance = buildServiceInstance(key, moduleMap.get(key));
                if(serviceInstance != null) {
                    serviceInstances.add(serviceInstance);
                }
                serviceInstanceMap.put(key, serviceInstances);
            }
        }
        @Override
        public String description() {
            return "DiscoveryClient for local development.";
        }
        @Override
        public List<ServiceInstance> getInstances(String serviceId) {
            return serviceInstanceMap.get(serviceId);
        }
        @Override
        public List<String> getServices() {
            return new ArrayList<>(serviceInstanceMap.keySet());
        }

        private ServiceInstance buildServiceInstance(String moduleName, String uriValue){
            try {
                LocalServiceInstance serviceInstance = new LocalServiceInstance();
                serviceInstance.setServiceId(moduleName);
                serviceInstance.setUri(new URI(uriValue));
                uriValue = uriValue.replace("http://", "");
                String[] uriParts = uriValue.split(":");
                serviceInstance.setHost(uriParts[0]);
                serviceInstance.setPort(Integer.parseInt(uriParts[1]));
                return serviceInstance;
            }
            catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
    }

    private class LocalServiceInstance implements ServiceInstance {
        private URI uri;
        private String serviceId;
        private String host;
        private int port;
        @Override
        public String getServiceId() {
            return serviceId;
        }
        @Override
        public String getHost() {
            return host;
        }
        @Override
        public int getPort() {
            return port;
        }
        @Override
        public boolean isSecure() {
            return false;
        }
        @Override
        public URI getUri() {
            return uri;
        }
        @Override
        public Map<String, String> getMetadata() {
            return null;
        }
        public void setUri(URI uri) {
            this.uri = uri;
        }
        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }
        public void setHost(String host) {
            this.host = host;
        }
        public void setPort(int port) {
            this.port = port;
        }
    }

}
