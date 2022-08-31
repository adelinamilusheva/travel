package bg.adi.travel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;

/**
 * Class that loads application-${spring.profiles.active}.properties. Please
 * note that -Dspring.profiles.active=XXX must be provided.
 */
@Configuration
@PropertySource(value = "classpath:application-${spring.profiles.active}.properties")
public class AppConfig {
    private Environment env;

    public AppConfig(Environment env) {
        getEnvironmentName();
    }

    public static String getEnvironmentName() {
        String env = System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
        if (env.contains(",")) {
            throw new RuntimeException("AppConfig supports single profile at a time. Current config: {}" + env);
        }

        return env;
    }

}

