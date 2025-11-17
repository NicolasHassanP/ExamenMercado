package org.example.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Mutant Detector API",
                version = "1.0.0",
                description = "API REST para el desafío de detección de secuencias mutantes en ADN. Incluye endpoints para clasificar ADN y obtener estadísticas.",
                contact = @Contact(
                        name = "Nicolas Hassan",
                        email = "nikohassanpv@gmail.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        )
)
public class SwaggerConfig {
}