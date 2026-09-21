package com.colab.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI/Swagger (RNF-16 — "contrato de API primero").
 *
 * <p>Expone la documentación interactiva en {@code /swagger-ui.html} y el
 * contrato JSON en {@code /v3/api-docs}. Documenta el esquema de seguridad JWT
 * para que los clientes (móvil y web) sepan cómo autenticar sus peticiones.</p>
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Colab — Backend de Usuario (API)")
                        .description("API REST del área de Usuario de Colab: autenticación, proyectos, "
                                + "equipo, generación de planificación con IA, gestión de tareas y notificaciones.")
                        .version("1.0.0")
                        .contact(new Contact().name("Equipo Colab")))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
