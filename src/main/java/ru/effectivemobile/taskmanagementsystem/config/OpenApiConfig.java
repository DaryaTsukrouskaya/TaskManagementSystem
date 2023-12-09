package ru.effectivemobile.taskmanagementsystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(security = {@SecurityRequirement(name = "Bearer Authentication")})
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicAuthApi() {
        return GroupedOpenApi.builder().group("auth").pathsToMatch("/**/auth/**").build();
    }


    @Bean
    public GroupedOpenApi publicTaskApi() {
        return GroupedOpenApi.builder().group("tasks").pathsToMatch("/**/task/**").build();
    }



    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().name("Eshop").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT");
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${application.description}") String appDescription,
                                 @Value("${application.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management System")
                        .version(appVersion)
                        .description(appDescription)
                        .license(new License().name("Apache 2.0")
                                .url("http://springdoc.org"))
                        .contact(new Contact().name("Darya Tsukrouskaya")
                                .email("darya.raikhert.31@mail.ru")))
                .servers(List.of(new Server().url("http://localhost:8080")
                        .description("Dev service"))).components(new Components().addSecuritySchemes("Bearer Authentication", createSecurityScheme()));
    }
}

