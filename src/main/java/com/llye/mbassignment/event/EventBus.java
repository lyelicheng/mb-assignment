package com.llye.mbassignment.event;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventBus {
    private List<EventHandler> handlers;

    public EventBus() {
        handlers = new ArrayList<>();
    }

    public void subscribe(EventHandler handler) {
        handlers.add(handler);
    }

    public void unsubscribe(EventHandler handler) {
        handlers.remove(handler);
    }

    public void publish(Event event) {
        for (EventHandler handler : handlers) {
            if (handler.canHandle(event)) {
                handler.handle(event);
            }
        }
    }
}
