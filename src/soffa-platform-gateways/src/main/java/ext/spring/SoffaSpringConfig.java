package ext.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@Configuration
@EnableRetry
@ComponentScan("io.soffa.platform.gateways")
public class SoffaSpringConfig {
}
