package io.soffa.core.pubsub;

public interface PubSubClient {

    void send(String channel, Event event);

    void send(Event event);

}
