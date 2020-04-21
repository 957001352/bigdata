package com.dhlk.hadoop.dhlk_hadoop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.dhlk.hadoop")
@SpringBootApplication
public class DhlkHadoopApplication {

    public static void main(String[] args) {
        SpringApplication.run(DhlkHadoopApplication.class, args);
    }

}
