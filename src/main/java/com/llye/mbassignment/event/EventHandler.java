package com.llye.mbassignment.event;

public interface EventHandler {
    boolean canHandle(Event event);
    void handle(Event event);
}
