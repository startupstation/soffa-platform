package io.soffa.platform.core.pubsub;

public interface PubSubClient {

    void send(String channel, Event event);

    void send(Event event);

    void sendDelayed(Event event, int ttlInSeconds);

    void sendDelayed(String channel, Event event, int ttlInSeconds);

}
