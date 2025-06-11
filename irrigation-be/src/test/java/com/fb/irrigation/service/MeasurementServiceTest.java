package com.fb.irrigation.service;

import com.fb.irrigation.repository.MeasurementRepository;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.SensorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}

