package com.reimbursement.health.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Health System API")
                        .version("1.0")
                        .description("This is the API documentation for the Reimbursement System")
                        .contact(new Contact()
                                .name("Paulo Lima")
                                .email("paulodlima2014@gmail.com")
                                .url("https://paulo.com")));
    }
}
