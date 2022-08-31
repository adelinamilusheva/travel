package bg.adi.travel.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestConfig {
	
	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
        		.setConnectTimeout(Duration.ofMillis(30000))
				.setReadTimeout(Duration.ofMillis(30000))
				.build();
    }
}
