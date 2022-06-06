package com.groww.chatbot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
// configuration class
// for swagger-v3 (OpenAPI) api documentation
public class SwaggerConfig {

    @Bean
    // returns a prepared Docket instance
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.groww.chatbot"))
                .build();
    }

    @Primary
    @Bean
    // openapi specifications config
    // uses src/main/resources/static/openapi.yaml file
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("Groww APIs");
            wsResource.setSwaggerVersion("2.0");
            wsResource.setLocation("/openapi.yaml");

            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            resources.clear();
            resources.add(wsResource);
            return resources;
        };
    }

    @Bean
    // OpenAPI UI configuration
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
                .docExpansion(DocExpansion.LIST)
                .build();
    }

}