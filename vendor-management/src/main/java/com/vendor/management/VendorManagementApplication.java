package com.vendor.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Vendor Management Spring Boot application.
 *
 * This class bootstraps and launches the application using
 * Spring Boot's auto-configuration and component scanning.
 */
@SpringBootApplication
public class VendorManagementApplication {

    /**
     * Main method used to run the Spring Boot application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(VendorManagementApplication.class, args);
    }
}