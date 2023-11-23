package com.zybzyb.liangyuoj.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    OpenAPI defaultOpenAPI() {
        return new OpenAPI().info(info())
            .externalDocs(documentation());
    }

    public Info info() {
        return new Info().title("Liangyu Online Judge")
            .version("V1.0.0")
            .description("良育OJ API文档");
    }

    public ExternalDocumentation documentation() {
        return new ExternalDocumentation().description("良育OJ");
    }

    @Bean
    GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
            .displayName("接口")
            .group("api")
            .packagesToScan("com.zybzyb.liangyuoj")
            .addOpenApiCustomizer(openApiCustomizer())
            .addOperationCustomizer(operationCustomizer())
            .build();
    }

    public OpenApiCustomizer openApiCustomizer() {
        return api -> api.getComponents()
            .addSecuritySchemes("Authorization", new SecurityScheme().name("认证")
                .type(SecurityScheme.Type.HTTP)
                .description("JWT 认证 `Bearer + ' ' + token`")
                .scheme("bearer")
                .bearerFormat("JWT"));
    }

    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            operation.addSecurityItem(new SecurityRequirement().addList("Authorization"));
            return operation;
        };
    }
}
