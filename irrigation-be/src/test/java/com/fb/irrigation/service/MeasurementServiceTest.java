package com.fb.irrigation.service;

import com.fb.irrigation.model.Measurement;
import com.fb.irrigation.model.MeasurementType;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import com.fb.irrigation.repository.MeasurementRepository;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.SensorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceTest {

    // === Mock dependencies ===
    @Mock
    private MeasurementRepository measurementRepository;

    @Mock
    private PlotRepository plotRepository;

    @Mock
    private SensorRepository sensorRepository;

    // === Inject mocks into service ===
    @InjectMocks
    private MeasurementServiceImpl measurementService;

    // === Your test method goes here ===
    @Test
    void shouldSetSnapshotFieldsWhenSavingMeasurement() {
        // Arrange
        Long plotId=1L;
        Long sensorId=10L;
        String plotName = "Test plot";
        String sensorName = "Test sensor";

        Plot plot= Plot.builder()
                .id(plotId)
                .name(plotName)
                .build();

        Sensor sensor= Sensor.builder()
                .id(sensorId)
                .name(sensorName)
                .build();

        Measurement inputMeasurement=Measurement.builder()
                .plot(plot)
                .sensor(sensor)
                .type(MeasurementType.HUMIDITY)
                .measuredAt(LocalDateTime.now())
                .measuredValue(42.0)
                .build();

        when(measurementRepository.save(any(Measurement.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        // Act
        Measurement savedMeasurement = measurementService.save(inputMeasurement);

        // Assert
        assertEquals(plotId, savedMeasurement.getPlotIdSnapshot());
        assertEquals(plotName, savedMeasurement.getPlotNameSnapshot());
        assertEquals(sensorId, savedMeasurement.getSensorIdSnapshot());
        assertEquals(sensorName, savedMeasurement.getSensorNameSnapshot());
    }
}

