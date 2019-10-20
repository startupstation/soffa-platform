package io.soffa.core.pubsub;


public interface PubSubListener {

    void receive(SimpleEvent event);

}
