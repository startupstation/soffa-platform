package io.soffa.adapters.spring.pubsub;

import io.soffa.core.JSON;
import io.soffa.core.pubsub.Event;
import io.soffa.core.pubsub.PubSubClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// @ConditionalOnBean(RabbitTemplate.class)
public class PubSubClientAdapter implements PubSubClient {

    @Value("${spring.application.name}")
    private String applicationName;

    private final RabbitTemplate rabbitTemplate;

    public PubSubClientAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Event event) {
        rabbitTemplate.convertAndSend(applicationName, JSON.serializeSafe(event).getBytes());
    }

    @Override
    public void send(String channel, Event event) {
        rabbitTemplate.convertAndSend(channel, JSON.serializeSafe(event).getBytes());
    }
}
