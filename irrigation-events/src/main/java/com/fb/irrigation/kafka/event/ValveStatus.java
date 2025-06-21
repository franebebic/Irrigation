package com.fb.irrigation.kafka.event;

public enum ValveStatus {
    OPEN,
    CLOSED;

    public ValveStatus toggle() {
        return this == OPEN ? CLOSED : OPEN;
    }
}
