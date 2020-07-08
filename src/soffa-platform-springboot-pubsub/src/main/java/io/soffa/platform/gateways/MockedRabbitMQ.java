package io.soffa.platform.gateways;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.GetResponse;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.HashMap;

@Configuration
@Order(0)
public class MockedRabbitMQ {

    @Value("${spring.application.name}")
    private String applicationName;

    @SneakyThrows
    @Bean
    @Profile({"test || offline"})
    CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(new MockConnectionFactory());
        try (Connection conn = factory.getRabbitConnectionFactory().newConnection()) {
            try (Channel channel = conn.createChannel()) {
                channel.queueDeclare(applicationName, false, true, true, new HashMap<>());
            }
        }
        return factory;
    }

}
