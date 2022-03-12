package com.sdase.docvalidator;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author akif
 * @since 3/11/22
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())//
			.paths(Predicates.not(PathSelectors.regex("/error")))//
			.build()
			.apiInfo(metadata())
			.useDefaultResponseMessages(false)//
			.securitySchemes(Collections.singletonList(apiKey()))
			.securityContexts(Collections.singletonList(securityContext()))
			.genericModelSubstitutes(Optional.class);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/v2/api-docs", "/v2/api-docs");
		registry.addRedirectViewController("/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
		registry.addRedirectViewController("/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
		registry.addRedirectViewController("/swagger-resources", "/swagger-resources");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()//
			.title("Document Validating API")//
			.description("This is a Document Validating API service. Doc files are published to the validating consumer."
				+ " The consumer validates the file. If the file is invalid, the consumer publishes it to a different consumer"
				+ " which deletes the file to avoid malware.")
			.version("1.0.0")
			.contact(new Contact(null, null, "tahsinanwar42@gmail.com"))//
			.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.any())
			.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
	}
}
