package com.fb.irrigation.service;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import com.fb.irrigation.mapper.MeasurementMapper;
import com.fb.irrigation.model.Measurement;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import com.fb.irrigation.repository.MeasurementRepository;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.SensorRepository;
import com.fb.irrigation.specification.MeasurementSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final PlotRepository plotRepository;
    private final SensorRepository sensorRepository;

    @Override
    public MeasurementDTO save(MeasurementCreateRequest createRequest) {
        Measurement savedMeasurement = createMeasurementFromCreateRequest(createRequest);
        return measurementMapper.toDTO(savedMeasurement);
    }

    @Override
    public void createFromMqtt(MeasurementCreateRequest dto) {
        createMeasurementFromCreateRequest(dto);
    }

    private Measurement createMeasurementFromCreateRequest(MeasurementCreateRequest createRequest) {
        Sensor sensor = sensorRepository.findByName(createRequest.getSensorName()).orElseThrow(() ->
                new EntityNotFoundException("Sensor with name " + createRequest.getSensorName() + " not found"));

        Plot plot = sensor.getPlot();
        if (plot == null)
            throw new IllegalStateException("Sensor with name " + sensor.getName() + " is not assigned to any plot");

        LocalDateTime localDateTime = LocalDateTime.now();

        Measurement measurement = Measurement.builder()
                .sensor(sensor)
                .sensorIdSnapshot(sensor.getId())
                .sensorNameSnapshot(sensor.getName())
                .plot(plot)
                .plotIdSnapshot(plot.getId())
                .plotNameSnapshot(plot.getName())
                .measuredAt(localDateTime)
                .measuredValue(createRequest.getMeasuredValue())
                .type(createRequest.getType())
                .build();

        return measurementRepository.save(measurement);
    }

    public MeasurementDTO save(MeasurementDTO dto) {
        Plot plot = plotRepository.findById(dto.getPlotId()).orElseThrow(() ->
                new EntityNotFoundException("Plot with id " + dto.getPlotId() + " not found"));
        Sensor sensor = sensorRepository.findById(dto.getSensorId()).orElseThrow(() ->
                new EntityNotFoundException("Sensor with id " + dto.getSensorId() + " not found"));

        Measurement measurement = measurementMapper.toEntity(dto, sensor, plot);
        return measurementMapper.toDTO(measurementRepository.save(measurement));
    }

    @Override
    public List<MeasurementDTO> findAll() {
        return measurementRepository.findAll().stream().map(measurementMapper::toDTO).toList();
    }

    @Override
    public Page<MeasurementDTO> findAll(int page, int size, String sensorName, String plotName) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "measuredAt"));
        Specification<Measurement> spec = Specification
                .where(MeasurementSpecification.sensorNameLike(sensorName))
                .and(MeasurementSpecification.plotNameLike(plotName));

        return measurementRepository.findAll(spec, pageable).map(measurementMapper::toDTO);
    }

    @Override
    public List<String> getSensorFilterOptions() {
        return measurementRepository.findDistinctSensorNames();
    }

    @Override
    public List<String> getPlotFilterOptions() {
        return measurementRepository.findDistinctPlotNames();
    }
}
