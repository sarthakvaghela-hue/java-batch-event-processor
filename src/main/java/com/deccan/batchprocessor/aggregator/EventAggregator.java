package com.deccan.batchprocessor.aggregator;

import com.deccan.batchprocessor.model.Event;
import com.deccan.batchprocessor.model.EventType;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class EventAggregator {

    private final Map<String, Map<EventType, Integer>> counts = new HashMap<>();
    private final Map<String, Integer> purchaseTotals = new HashMap<>();

    public void accept(Event event) {
        counts.computeIfAbsent(event.getUserId(), k -> new EnumMap<>(EventType.class))
              .merge(event.getEventType(), 1, Integer::sum);

        if (event.getEventType() == EventType.PURCHASE) {
            purchaseTotals.merge(event.getUserId(), event.getValue(), Integer::sum);
        }
    }

    public Map<String, Map<EventType, Integer>> getCounts() {
        return counts;
    }

    public Map<String, Integer> getPurchaseTotals() {
        return purchaseTotals;
    }
}