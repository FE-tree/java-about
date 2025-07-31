package org.example.first;

import org.example.first.service.SimpleHttpServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FirstMain {
    public static void main(String[] args) throws Exception {
        SimpleHttpServer.main(args);

        SpringApplication.run(FirstMain.class, args);
    }
}
