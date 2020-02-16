package com.andrew.solutions.tictactoe.config;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

/**
 * http://localhost:8080/swagger-ui.html
 *
 * @author Andrew Xie
 * @Date 2020-02-13
 * @GitHub : https://github.com/andrew-xie-job/tic-tac-toe
 */

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    private static final Predicate<String> PATHS = PathSelectors.regex("/.*");
    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMERS = new HashSet<>(Arrays.asList("application/hal+json"));

    private final String version;

    public SwaggerConfig(@Value("${info.app.version}") String version) {
        this.version = version;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.andrew.solutions.tictactoe"))
                .paths(PATHS)
                .build()
                .apiInfo(apiInfo())
                .produces(DEFAULT_PRODUCES_AND_CONSUMERS)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMERS);
    }

    @Bean
    public LinkDiscoverers discoverers() {
        List<LinkDiscoverer> plugins = new ArrayList<>();
        plugins.add(new CollectionJsonLinkDiscoverer());
        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Tic Tac Toe Rest API")
                .description("Tic Tac Toe Rest API Demo App")
                .version(version)
                .build();
    }
}
