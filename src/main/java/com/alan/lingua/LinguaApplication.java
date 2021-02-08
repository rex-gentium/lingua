package com.alan.lingua;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@Log4j2
@EnableWebFlux
@SpringBootApplication
public class LinguaApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(LinguaApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Application started!");
    }
}
