package com.example.bootconfigdemo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@ConditionalOnProperty("app.event-queue.enabled")
public class EventQueue {

    private final BlockingQueue<SomeEvent> queue = new LinkedBlockingQueue<>();

    public void enqueue(SomeEvent someEvent) {
        try {
            queue.put(someEvent);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public SomeEvent dequeue() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
