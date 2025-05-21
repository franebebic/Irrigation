package com.fb.irrigation.service;

import com.fb.irrigation.model.Measurement;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import com.fb.irrigation.repository.MeasurementRepository;

public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    public Measurement save(Measurement inputMeasurement) {

        Plot plot=inputMeasurement.getPlot();
        Sensor sensor=inputMeasurement.getSensor();
        inputMeasurement.setPlotIdSnapshot(plot.getId());
        inputMeasurement.setPlotNameSnapshot(plot.getName());
        inputMeasurement.setSensorIdSnapshot(sensor.getId());
        inputMeasurement.setSensorNameSnapshot(sensor.getName());

        return measurementRepository.save(inputMeasurement);
    }
}
