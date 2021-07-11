package com.nidhi.cms.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import com.nidhi.cms.constants.SwaggerConstant;

import io.swagger.annotations.Api;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 *
 * @author Ankur Bansala
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
    public static final String VERSION_GROUP_NAME = "v1";

    public static final Contact DEFAULT_CONTACT = new Contact("Nidhi CMS", "https://www.nidhicms.com/", "support@nidhiCMS.com");

    public static final ApiInfo DEFAULT_API_INFO =
        new ApiInfo("Nidhi CMS", "RestFul API", VERSION_GROUP_NAME, "", DEFAULT_CONTACT, "", "", new ArrayList<>());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList(MediaType.APPLICATION_JSON_VALUE));

    @Bean
    public Docket apiDocket(final ServletContext context) {
        int tagOrder = 1;

        return new Docket(DocumentationType.SWAGGER_2)
            .pathProvider(new RelativePathProvider(context) {
                @Override
                public String getApplicationBasePath() {
                    return "/";
                }
            })
            .apiInfo(DEFAULT_API_INFO).groupName(VERSION_GROUP_NAME).select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.any()).build()
            .useDefaultResponseMessages(false)
            .securitySchemes(Arrays.asList(apiKey(), securitySchema()))
            .produces(DEFAULT_PRODUCES_AND_CONSUMES)
            .tags(new Tag(SwaggerConstant.ApiTag.LOGIN, "", tagOrder), getApiOrderedTags(tagOrder));
    }

    /**
     * We just need to define a Swagger Tag here to fix/customise the ordered Display. Otherwise, the tags will appear in ALPHA
     * order {@link SwaggerConfig#uiConfig()} in the API documentation.
     */
    private Tag[] getApiOrderedTags(int tagOrder) {
        return new Tag[] {
            new Tag(SwaggerConstant.ApiTag.USER, "", tagOrder),
            new Tag(SwaggerConstant.ApiTag.OTP, "", tagOrder++)
        };
    }

    private ApiKey apiKey() {
        return new ApiKey("accessToken", "Authorization", "header");
    }

    private OAuth securitySchema() {
        final List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        final List<GrantType> grantTypes = new ArrayList<>();
        return new OAuth("oauthToken", authorizationScopeList, grantTypes);
    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .useBasicAuthenticationWithAccessCodeGrant(false)
            .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .deepLinking(false)
            .displayOperationId(false)
            .defaultModelsExpandDepth(0)
            .defaultModelRendering(ModelRendering.MODEL)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.LIST)
            .filter(true)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(true)
            .tagsSorter(TagsSorter.ALPHA)
            .validatorUrl("")
            .displayRequestDuration(true)
            .build();
    }

}
