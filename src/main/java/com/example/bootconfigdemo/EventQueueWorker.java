package com.example.bootconfigdemo;

import com.example.bootconfigdemo.event.EventHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@RequiredArgsConstructor
public class EventQueueWorker {

    private final EventQueue eventQueue;

    private final ApplicationEventPublisher eventPublisher;

    @EventListener(ApplicationReadyEvent.class)
    public void startEventQueue() {
        startEventProducer();
        startEventConsumer();
    }

    private void startEventProducer() {
        Thread eventProducerThread = new Thread(() -> {
            while (true) {
                try {
                UUID id = UUID.randomUUID();
                SomeEvent someEvent = SomeEvent.builder()
                        .id(id)
                        .payload("Payload for event " + id)
                        .build();

                eventQueue.enqueue(someEvent);
                Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        eventProducerThread.start();
    }

    private void startEventConsumer() {
        Thread eventConsumerThread = new Thread(() -> {
            while (true) {
                SomeEvent someEvent = eventQueue.dequeue();
               // System.out.println(someEvent);
                eventPublisher.publishEvent(new EventHolder(this, someEvent));
            }
        });
        eventConsumerThread.start();
    }
}
