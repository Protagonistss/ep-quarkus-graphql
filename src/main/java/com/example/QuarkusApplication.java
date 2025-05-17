package com.example;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
@ApplicationPath("/")
public class QuarkusApplication extends Application {
    
    public static void main(String[] args) {
        Quarkus.run(args);
    }
}