package io.soffa.platform.core.pubsub;


public interface PubSubListener {

    void receive(SimpleEvent event);

}
