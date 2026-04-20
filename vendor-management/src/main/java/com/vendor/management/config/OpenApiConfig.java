package com.vendor.management.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI (Swagger) documentation.
 *
 * This class defines metadata for the API such as title, version,
 * description, and contact information. The configuration is used
 * by Swagger UI to generate interactive API documentation.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Creates a custom OpenAPI configuration bean.
     *
     * This method sets up basic API information including:
     * - API title
     * - Version
     * - Description
     * - Contact details
     *
     * @return configured OpenAPI instance used for Swagger documentation
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vendor Management API")
                        .version("1.0")
                        .description("REST API for managing vendors, categories, and payouts")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev@example.com")));
    }
}