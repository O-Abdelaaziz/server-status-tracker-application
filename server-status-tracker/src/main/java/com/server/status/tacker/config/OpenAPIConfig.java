package com.server.status.tacker.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * @Created 24/06/2024 - 13:14
 * @Package com.server.status.tacker.config
 * @Project server-status-tracker
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Configuration
public class OpenAPIConfig {

    @Value("${openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        //  Server prodServer = new Server();
        //  prodServer.setUrl(prodUrl);
        //  prodServer.setDescription("Server URL in Production environment");

        Contact contact = new Contact();
        contact.setEmail("mr.ouakala.abdelaaziz@gmail.com");
        contact.setName("O.Abdelaaziz");
        contact.setUrl("http://ouakala-abdelaaziz.epizy.com/");

        License mitLicense = new License().name("MIT License").url("https://choosealicense.com/licenses/mit/");

        Info info = new Info()
                .title("Server Status Tacker API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to track server status.").termsOfService("http://ouakala-abdelaaziz.epizy.com/")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
