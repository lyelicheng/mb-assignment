package com.llye.mbassignment.event;

import java.time.ZonedDateTime;

public interface Event {
    ZonedDateTime getTimestamp();
    String getEventType();
    String getEventSource();
}
