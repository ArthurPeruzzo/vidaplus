package com.uninter.vidaplus.infra.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        tags = {
                @Tag(name = "Autenticação", description = "Autenticação"),
                @Tag(name = "Administradores", description = "Operações relacionadas aos administradores"),
                @Tag(name = "Unidades Hospilares", description = "Operações relacionadas as unidades hospitalares"),
                @Tag(name = "Profissionais da Saúde", description = "Operações relacionadas aos profissionais da saúde"),
                @Tag(name = "Pacientes", description = "Operações relacionadas aos pacientes"),
                @Tag(name = "Agendas", description = "Operações relacionadas a agenda do profissional da saúde"),
                @Tag(name = "Consultas", description = "Gerenciamento de consultas"),
        },
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}


