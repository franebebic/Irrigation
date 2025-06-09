package com.fb.irrigation.model;

public enum ValveStatus {
    OPEN,
    CLOSED;

    public ValveStatus toggle() {
        return this == OPEN ? CLOSED : OPEN;
    }
}
