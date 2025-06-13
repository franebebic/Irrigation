package com.fb.irrigation.repository;

import com.fb.irrigation.model.Measurement;
import com.fb.irrigation.model.MeasurementType;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class MeasurementRepositoryTest {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private PlotRepository plotRepository;

    @Autowired
    private SensorRepository sensorRepository;

    @Test
    void shouldPersistMeasurementWithSnapshots() {
        // Arrange
        Plot plot= Plot.builder()
                .name("Test Plot")
                .build();
        Sensor sensor=Sensor.builder()
                .name("Test Sensor")
                .build();

        plot=plotRepository.save(plot);
        sensor=sensorRepository.save(sensor);

        Measurement measurement=Measurement.builder()
                .plot(plot)
                .plotIdSnapshot(plot.getId())
                .plotNameSnapshot(plot.getName())
                .sensor(sensor)
                .sensorIdSnapshot(sensor.getId())
                .sensorNameSnapshot(sensor.getName())
                .type(MeasurementType.HUMIDITY)
                .measuredAt(Instant.now())
                .measuredValue(18.2)
                .build();

        //Act
        Measurement saved=measurementRepository.save(measurement);

        //Assert
        assertNotNull(saved.getId());
        assertEquals(plot.getId(), saved.getPlotIdSnapshot());
        assertEquals(plot.getName(), saved.getPlotNameSnapshot());
        assertEquals(sensor.getId(), saved.getSensorIdSnapshot());
        assertEquals(sensor.getName(), saved.getSensorNameSnapshot());
    }
}
