package com.example.bootconfigdemo.event;

import com.example.bootconfigdemo.SomeEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventHolder extends ApplicationEvent {

    private final SomeEvent someEvent;


    public EventHolder(Object source, SomeEvent someEvent) {
        super(source);
        this.someEvent = someEvent;
    }
}
