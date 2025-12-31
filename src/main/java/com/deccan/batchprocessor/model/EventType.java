package com.deccan.batchprocessor.model;

public enum EventType {
	 	LOGIN,
	    LOGOUT,
	    PURCHASE,
	    ERROR;

	    public static EventType fromString(String value) {
	        return EventType.valueOf(value.trim().toUpperCase());
	    }
}
