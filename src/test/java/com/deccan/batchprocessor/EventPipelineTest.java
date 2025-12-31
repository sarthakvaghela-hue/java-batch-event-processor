package com.deccan.batchprocessor;

import com.deccan.batchprocessor.aggregator.EventAggregator;
import com.deccan.batchprocessor.model.Event;
import com.deccan.batchprocessor.model.EventType;
import com.deccan.batchprocessor.parser.EventParser;
import com.deccan.batchprocessor.validator.EventValidator;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EventPipelineTest {

	// Valid input line should pass basic validation
    @Test
    void validLineShouldPassValidation() {
        EventValidator validator = new EventValidator();
        boolean result = validator.isValid("u1,LOGIN,0,1714300000000");
        assertTrue(result);
    }

    // Unknown event types must be rejected
    @Test
    void invalidEventTypeShouldFailValidation() {
        EventValidator validator = new EventValidator();
        boolean result = validator.isValid("u1,PAYMENT,100,1714300000000");
        assertFalse(result);
    }

    // Negative numeric values are not allowed
    @Test
    void negativeValueShouldFailValidation() {
        EventValidator validator = new EventValidator();
        boolean result = validator.isValid("u1,PURCHASE,-10,1714300000000");
        assertFalse(result);
    }

    // Parser should correctly convert a valid line into an Event object
    @Test
    void parserShouldCreateEventCorrectly() {
        EventParser parser = new EventParser();
        Event event = parser.parse("u1,PURCHASE,200,1714300000000");

        assertEquals("u1", event.getUserId());
        assertEquals(EventType.PURCHASE, event.getEventType());
        assertEquals(200, event.getValue());
        assertEquals(1714300000000L, event.getTimestamp());
    }

    // Aggregator should count event occurrences per user correctly
    @Test
    void aggregatorShouldCountEventsPerUser() {
        EventAggregator aggregator = new EventAggregator();

        aggregator.accept(new Event("u1", EventType.LOGIN, 0, 1L));
        aggregator.accept(new Event("u1", EventType.LOGIN, 0, 2L));
        aggregator.accept(new Event("u1", EventType.LOGOUT, 0, 3L));

        Map<EventType, Integer> counts = aggregator.getCounts().get("u1");

        assertEquals(2, counts.get(EventType.LOGIN));
        assertEquals(1, counts.get(EventType.LOGOUT));
    }

    // Only PURCHASE events should contribute to total purchase value
    @Test
    void aggregatorShouldSumPurchaseValuesOnly() {
        EventAggregator aggregator = new EventAggregator();

        aggregator.accept(new Event("u2", EventType.PURCHASE, 100, 1L));
        aggregator.accept(new Event("u2", EventType.PURCHASE, 150, 2L));
        aggregator.accept(new Event("u2", EventType.LOGIN, 0, 3L));

        int total = aggregator.getPurchaseTotals().get("u2");

        assertEquals(250, total);
    }
}