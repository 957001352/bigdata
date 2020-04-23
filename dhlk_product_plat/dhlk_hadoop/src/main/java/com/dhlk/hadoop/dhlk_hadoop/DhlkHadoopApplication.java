package com.dhlk.hadoop.dhlk_hadoop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@ComponentScan("com.dhlk.hadoop")
@EnableDiscoveryClient
@SpringBootApplication
public class DhlkHadoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkHadoopApplication.class, args);
    }

}
