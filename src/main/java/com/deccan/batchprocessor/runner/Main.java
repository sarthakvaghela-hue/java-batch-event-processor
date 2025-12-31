package com.deccan.batchprocessor.runner;

import com.deccan.batchprocessor.aggregator.EventAggregator;
import com.deccan.batchprocessor.model.Event;
import com.deccan.batchprocessor.parser.EventParser;
import com.deccan.batchprocessor.reader.EventFileReader;
import com.deccan.batchprocessor.validator.EventValidator;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        int batchSize = 5;

        EventFileReader reader = new EventFileReader();
        EventValidator validator = new EventValidator();
        EventParser parser = new EventParser();
        EventAggregator aggregator = new EventAggregator();

        List<String> errors = new ArrayList<>();
        BufferedReader bufferedReader = reader.open("events_input.txt");

        while (true) {
            List<String> batch = reader.readBatch(bufferedReader, batchSize);
            if (batch.isEmpty()) {
                break;
            }

            for (String line : batch) {
                if (!validator.isValid(line)) {
                    errors.add(line);
                    continue;
                }
                Event event = parser.parse(line);
                aggregator.accept(event);
            }
        }

        System.out.println("=== Summary Report ===");
        for (Map.Entry<String, Map<com.deccan.batchprocessor.model.EventType, Integer>> entry : aggregator.getCounts().entrySet()) {
            System.out.println("User: " + entry.getKey());
            entry.getValue().forEach((k, v) -> System.out.println("  " + k + ": " + v));
            int total = aggregator.getPurchaseTotals().getOrDefault(entry.getKey(), 0);
            System.out.println("  TOTAL_PURCHASE_VALUE: " + total);
            System.out.println();
        }

        System.out.println("=== Error Report ===");
        for (String error : errors) {
            System.out.println(error);
        }
    }
}
