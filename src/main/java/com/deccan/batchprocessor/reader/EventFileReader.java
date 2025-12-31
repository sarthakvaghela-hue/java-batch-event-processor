package com.deccan.batchprocessor.reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EventFileReader {

    public List<String> readBatch(BufferedReader reader, int batchSize) throws Exception {
        List<String> batch = new ArrayList<>();
        String line;
        while (batch.size() < batchSize && (line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                batch.add(line);
            }
        }
        return batch;
    }

    public BufferedReader open(String resourceName) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceName);
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
