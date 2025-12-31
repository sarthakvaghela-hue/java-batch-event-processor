package com.deccan.batchprocessor.parser;

import com.deccan.batchprocessor.model.Event;
import com.deccan.batchprocessor.model.EventType;

public class EventParser {

	public Event parse(String line) {
		String[] parts = line.split(",", -1);
		String userId = parts[0].trim();
		EventType type = EventType.fromString(parts[1]);
		int value = Integer.parseInt(parts[2].trim());
		long timestamp = Long.parseLong(parts[3].trim());
		
		return new Event(userId, type, value, timestamp);
	}
}
