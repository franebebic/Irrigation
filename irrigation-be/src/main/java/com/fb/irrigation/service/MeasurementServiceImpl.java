package com.fb.irrigation.service;

import com.fb.irrigation.dto.MeasurementCreateRequest;
import com.fb.irrigation.dto.MeasurementDTO;
import com.fb.irrigation.kafka.event.MeasurementEvent;
import com.fb.irrigation.kafka.service.KafkaProducerService;
import com.fb.irrigation.mapper.MeasurementMapper;
import com.fb.irrigation.model.Measurement;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import com.fb.irrigation.repository.MeasurementRepository;
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

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final MeasurementMapper measurementMapper;
    private final SensorRepository sensorRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void createFromMqtt(MeasurementCreateRequest dto) {
        Sensor sensor = sensorRepository.findByName(dto.getSensorName()).orElseThrow(() ->
                new EntityNotFoundException("Sensor with name " + dto.getSensorName() + " not found"));

        Plot plot = sensor.getPlot();
        if (plot == null)
            throw new IllegalStateException("Sensor with name " + sensor.getName() + " is not assigned to any plot");

        Measurement measurement = Measurement.builder()
                .sensor(sensor)
                .sensorIdSnapshot(sensor.getId())
                .sensorNameSnapshot(sensor.getName())
                .plot(plot)
                .plotIdSnapshot(plot.getId())
                .plotNameSnapshot(plot.getName())
                .measuredAt(Instant.now())
                .measuredValue(dto.getMeasuredValue())
                .type(dto.getType())
                .build();

        measurementRepository.save(measurement);

        publishKafkaEvent(measurement);
    }

    private void publishKafkaEvent(Measurement measurement) {
        MeasurementEvent measurementEvent=MeasurementEvent.builder()
                .plotId(measurement.getPlot().getId())
                .sensorId(measurement.getSensor().getId())
                .sensorType(measurement.getType().name())
                .measuredValue(measurement.getMeasuredValue())
                .measuredAt(measurement.getMeasuredAt())
                .build();
        kafkaProducerService.publishMeasurementEvent(measurementEvent);
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
