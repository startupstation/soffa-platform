package ext.spring;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@ComponentScan("io.soffa.platform.gateways")
public class SoffaSpringConfig {

    @Bean
    @ConditionalOnMissingBean(OpenAPI.class)
    public OpenAPI apiInfo(
        @Value("${spring.application.name}") String appName,
        @Value("${service.version}") String appVersion
    ) {
        return new OpenAPI()
            .components(new Components())
            .info(new Info()
                .title(appName)
                .version(appVersion)
                .description(appName));
    }

}
