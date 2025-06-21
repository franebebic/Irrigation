package com.fb.irrigation_decision_service.rule;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FactKey {
    MOISTURE("moisture"),
    MOISTURE_MIN_THRESHOLD("moistureMinThreshold"),
    MOISTURE_MAX_THRESHOLD("moistureMaxThreshold"),
    RAIN_EXPECTED_IN_NEXT_12H("rainExpectedInNext12h"),
    SHOULD_START_IRRIGATION("shouldStartIrrigation"),
    SHOULD_STOP_IRRIGATION("shouldStopIrrigation"),
    VALVE_CURRENT_STATUS("valveCurrentStatus"),
    IRRIGATION_COMMAND("irrigationCommand");


    private final String key;

    @Override
    public String toString() {
        return key;
    }
}