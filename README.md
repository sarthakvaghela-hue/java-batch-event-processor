# Java Batch Event Processor

This project is a simple batch-processing application written in Java.  
It reads an event file from the classpath, validates each record, aggregates user-level statistics, and prints results to the console.

## Input Format

Each line in the input file represents an event in the following format:

userId,eventType,value,timestamp

Valid event types are:
LOGIN, LOGOUT, PURCHASE, ERROR

The input file is located under:
src/main/resources/events_input.txt

## Processing Rules

- The file is read incrementally and processed in fixed-size batches
- Records are validated for basic format and data errors
- Invalid records are collected and reported separately
- Valid records are aggregated per user
- Event counts are maintained per event type
- Purchase values are summed per user

## Output

The application prints:
- A summary report showing per-user event counts and total purchase value
- An error report listing invalid input records

All output is written to the console only.

## Constraints

- Java 8+
- No external libraries
- Modular, multi-stage pipeline design

## Running the Application

Run the main class:

com.company.batchprocessor.runner.Main
