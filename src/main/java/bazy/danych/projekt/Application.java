package bazy.danych.projekt;

import bazy.danych.projekt.db.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Connector.initConnection();
        SpringApplication.run(Application.class, args);
    }

}
