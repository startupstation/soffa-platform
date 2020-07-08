package io.soffa.platform.gateways;

import com.github.fridujo.rabbitmq.mock.MockConnectionFactory;
import io.soffa.platform.core.data.JSON;
import io.soffa.platform.core.pubsub.PubSubListener;
import io.soffa.platform.core.pubsub.SimpleEvent;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQAutoConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    private final PubSubListener receiver;

    @Autowired(required = false)
    public RabbitMQAutoConfig(PubSubListener receiver) {
        this.receiver = receiver;
    }

    @Bean
    Queue queue() {
        return new Queue(applicationName, true);
    }

    @Bean
    CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(applicationName, "x-delayed-message", true, false, args);
    }

    @Bean
    Binding binding(Queue queue, Exchange delayExchange) {
        return BindingBuilder.bind(queue).to(delayExchange).with("default").noargs();
    }

    @RabbitListener(queues = "${spring.application.name}")
    public void listen(byte[] received) {
        if (receiver != null) {
            Map<String, Object> data = JSON.toMap(new String(received));
            String eventId = (String) data.get("eventId");
            Map<String, Object> payload = JSON.toMap(data.get("payload"));
            receiver.receive(new SimpleEvent(eventId, payload));
        }
    }

}
