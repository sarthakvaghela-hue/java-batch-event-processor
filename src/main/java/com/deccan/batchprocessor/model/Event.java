package com.deccan.batchprocessor.model;

public class Event {
	private final String userId;
    private final EventType eventType;
    private final int value;
    private final long timestamp;

    public Event(String userId, EventType eventType, int value, long timestamp) {
        this.userId = userId;
        this.eventType = eventType;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public int getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
