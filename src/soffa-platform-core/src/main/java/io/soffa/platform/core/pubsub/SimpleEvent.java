package io.soffa.platform.core.pubsub;

import java.util.Map;

public class SimpleEvent implements Event {

    private String eventId;
    private Map<String, Object> payload;

    public SimpleEvent(String eventId) {
        this.eventId = eventId;
    }

    public SimpleEvent(String eventId, Map<String, Object> payload) {
        this.eventId = eventId;
        this.payload = payload;
    }

    public String getEventId() {
        return eventId;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (payload==null) return null;
        return (T)payload.get(key);
    }

}
