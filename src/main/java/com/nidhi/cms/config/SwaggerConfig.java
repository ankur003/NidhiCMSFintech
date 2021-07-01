package com.nidhi.cms.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.nidhi.cms.constants.JwtConstants.HEADER_STRING;

/**
 * 
 *
 * @author Ankur Bansala
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
				.pathMapping("/").apiInfo(apiInfo()).useDefaultResponseMessages(false)
				.securityContexts(Lists.newArrayList(securityContext())).securitySchemes(Lists.newArrayList(apiKey()));
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().displayRequestDuration(true).filter(true).build();

	}

	/** * This method is use for get apiInfo * @param Nothing. * @return ApiInfo. */
	public ApiInfo apiInfo() {
		final ApiInfoBuilder builder = new ApiInfoBuilder();
		builder.title("Nidhi CMS service API").version("1.0").license("(C) Copyright Nidhi CMS")
				.description("The API provides a platform to query build Nidhi CMS api")
				.contact(new Contact("Nidhi CMS", "http://NidhiCMS.org", "NidhiCMS@NidhiCMS.com"));
		return builder.build();
	}

	private ApiKey apiKey() {
		return new ApiKey(HEADER_STRING, HEADER_STRING, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Lists.newArrayList(new SecurityReference(HEADER_STRING, authorizationScopes));
	}
}
