package it.snorcini.dev.orderplanner.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;

import java.util.List;

/**
 * Swagger service spring configuration.
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "orderplanner API",
        version = "1.0",
        description = "orderplanner panel info"))
public class SwaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
    public static final String AUTH_HEADER = "KC_ACCESS_TOKEN";
    public static final String AUTH_SCOPE = "openid";

    /**
     * Bean declared to define Swagger Docket.
     *
     * @return The Docket.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .paths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
                .build()
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .apiInfo(apiInfo());
    }

    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .description("API for the orderplanner server interaction")
                .title("orderplanner API")
                .version("1.0.0")
                .build();
    }

    private static ApiKey apiKey() {
        return new ApiKey(AUTH_HEADER, AUTHORIZATION_HEADER, ApiKeyVehicle.HEADER.name());
    }

    private static SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
    }

    private static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(AUTH_SCOPE, AUTH_SCOPE);
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference(AUTH_HEADER, authorizationScopes));
    }
}
