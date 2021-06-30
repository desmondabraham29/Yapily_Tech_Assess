package com.yapily.tech.asses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.yapily.tech.asses.*"})
public class ApplicationLauncher {
    public static void main(String[] args) {
            SpringApplication.run(ApplicationLauncher.class, args);
    }
}
