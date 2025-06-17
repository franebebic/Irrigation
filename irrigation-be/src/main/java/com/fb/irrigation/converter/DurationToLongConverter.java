package com.fb.irrigation.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.Duration;

@Converter(autoApply = false)
public class DurationToLongConverter implements AttributeConverter<Duration, Long> {

    @Override
    public Long convertToDatabaseColumn(Duration duration) {
        return (duration != null) ? duration.getSeconds() : null;
    }

    @Override
    public Duration convertToEntityAttribute(Long seconds) {
        return (seconds != null) ? Duration.ofSeconds(seconds) : null;
    }
}