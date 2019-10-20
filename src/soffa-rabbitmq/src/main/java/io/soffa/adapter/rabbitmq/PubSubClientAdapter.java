package io.soffa.adapter.rabbitmq;

import io.soffa.core.data.JSON;
import io.soffa.core.pubsub.Event;
import io.soffa.core.pubsub.PubSubClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PubSubClientAdapter implements PubSubClient {

    public static final String DEFAULT_ROUTING_KEY = "default";

    @Value("${spring.application.name}")
    private String applicationName;

    private final RabbitTemplate rabbitTemplate;

    public PubSubClientAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void send(Event event) {
        rabbitTemplate.convertAndSend(applicationName, DEFAULT_ROUTING_KEY, JSON.serializeSafe(event).getBytes());
    }

    @Override
    public void send(String channel, Event event) {
        rabbitTemplate.convertAndSend(channel, DEFAULT_ROUTING_KEY, JSON.serializeSafe(event).getBytes());
    }

    @Override
    public void sendDelayed(String channel, Event event, int ttlInSeconds) {
        rabbitTemplate.convertAndSend(channel, DEFAULT_ROUTING_KEY, JSON.serializeSafe(event).getBytes(), message -> {
            message.getMessageProperties().setHeader("x-delay", TimeUnit.SECONDS.toMillis(ttlInSeconds));
            return message;
        });
    }

    @Override
    public void sendDelayed(Event event, int ttlInSeconds) {
        rabbitTemplate.convertAndSend(applicationName, DEFAULT_ROUTING_KEY, JSON.serializeSafe(event).getBytes(), message -> {
            message.getMessageProperties().setHeader("x-delay", TimeUnit.SECONDS.toMillis(ttlInSeconds));
            return message;
        });
    }
}
