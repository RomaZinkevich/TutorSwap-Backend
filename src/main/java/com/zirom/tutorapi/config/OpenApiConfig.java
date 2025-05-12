package com.zirom.tutorapi.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@OpenAPIDefinition(
//        info = @Info(title = "TutorSwap API", version = "1.0.0"),
//        servers = {
//                @Server(
//                        description = "Local ENV",
//                        url = "http://localhost:8080"
//                ),
//                @Server(
//                        description = "Prod ENV",
//                        url = "https://tutorswap.190304.xyz"
//                ),
//        }
//)
@SecurityScheme(
        name = "BearerAuth",
        description = "JWT auth",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
@Configuration
public class OpenApiConfig {

    @Value("${SPRING_PROFILES_ACTIVE}")
    private String activeProfile;

    @Bean
    public OpenAPI customOpenAPI() {
        String url = activeProfile.equals("prod")
                ? "https://tutorswap.190304.xyz"
                : "http://localhost:8080";

        return new OpenAPI()
                .info(new Info().title("TutorSwap API").version("1.0.0"))
                .servers(List.of(new Server().url(url).description(activeProfile + " ENV")));
    }
}
