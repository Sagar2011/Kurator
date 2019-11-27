package com.kurator.kuratorDocumentService.firewall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.kurator.kuratorDocumentService.firewall", "com.kurator.kuratorDocumentService.library"})
public class FirewallApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirewallApplication.class, args);
    }

}
