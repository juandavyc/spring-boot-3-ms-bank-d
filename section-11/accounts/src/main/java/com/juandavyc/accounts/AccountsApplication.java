package com.juandavyc.accounts;

import com.juandavyc.accounts.dto.AccountContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "AuditAwareImpl")
@EnableConfigurationProperties(value = {AccountContactInfoDto.class})
@EnableFeignClients
@OpenAPIDefinition(
        info = @Info(
                title = "Accounts microservice REST API Documentation",
                description = "juandavyc microservices REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Juan David",
                        email = "juan.yara@gmail.com",
                        url = "https://juandavyc.netlify.app"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://juandavyc.netlify.app/license"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "juandavyc microservices REST API Documentation",
                url = "https://juandavyc.netlify.app/swagger-ui"
        )

)


public class AccountsApplication {

    public static void main(String[] args) {

        SpringApplication.run(AccountsApplication.class, args);
    }


}
