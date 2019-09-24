package io.soffa.adapters.spring.pubsub;

import io.soffa.core.JSON;
import io.soffa.core.pubsub.PubSubListener;
import io.soffa.core.pubsub.SimpleEvent;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @RabbitListener(queues = "${spring.application.name}")
    public void listen(byte[] received) {
        if (receiver != null) {
            Map<String, Object> data = JSON.toMap(new String(received));
            String eventId = (String) data.get("eventId");
            receiver.receive(new SimpleEvent(eventId, (Map)data.get("payload")));
        }
    }

}
