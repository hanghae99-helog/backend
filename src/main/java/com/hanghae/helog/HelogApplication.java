package com.hanghae.helog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelogApplication.class, args);
    }

}
