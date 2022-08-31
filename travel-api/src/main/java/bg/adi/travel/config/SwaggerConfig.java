package bg.adi.travel.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@EnableSwagger2
public class SwaggerConfig {
	
	// .../api/swagger-ui/index.html
	@Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.any())              
          .paths(PathSelectors.any())                      
          .build()
          .apiInfo(apiInfo());                                           
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
            "Travel Service REST API",
            "OpenAPI schema of the Travel REST API.",
            "v0.1.0",
            "Terms of service",
            new Contact("Travel.com Ltd.", "www.travel.com", "office@travel.com"),
            "License of API", "API license URL", Collections.emptyList()
        );
    }
}
