package io.soffa.adapters.spring.pubsub;

import io.soffa.core.JSON;
import io.soffa.core.pubsub.PubSubListener;
import io.soffa.core.pubsub.SimpleEvent;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Configuration
//@ConditionalOnBean(PubSubListener.class)
public class RabbitMQAutoConfig {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired(required = false)
    private PubSubListener receiver;

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
            receiver.receive(new SimpleEvent(eventId, (Map)data.get("payload")));
        }
    }

}
