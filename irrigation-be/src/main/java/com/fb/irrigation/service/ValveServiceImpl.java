package com.fb.irrigation.service;

import com.fb.irrigation.dto.ValveDTO;
import com.fb.irrigation.mapper.ValveMapper;
import com.fb.irrigation.model.*;
import com.fb.irrigation.mqtt.MqttProperties;
import com.fb.irrigation.mqtt.MqttPublisher;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.ValveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValveServiceImpl implements ValveService {
    private final ValveMapper valveMapper;
    private final ValveRepository valveRepository;
    private final PlotRepository plotRepository;
    private final MqttPublisher mqttPublisher;
    private final ActivityService activityService;
    private final MqttProperties mqttProperties;

    @Override
    public ValveDTO save(ValveDTO dto) {
        Plot plot = null;
        if (dto.getPlotId() != null)
            plot = plotRepository.findById(dto.getPlotId()).orElseThrow(() -> new EntityNotFoundException("Plot with id " + dto.getPlotId() + " not found"));

        if (dto.getStatus() == null)
            dto.setStatus(ValveStatus.CLOSED);

        Optional<Valve> existing = valveRepository.findByPlotId(dto.getPlotId());
        if (existing.isPresent() && !existing.get().getId().equals(dto.getId())) {
            throw new IllegalStateException("Plot '" + existing.get().getPlot().getName() + "' already has valve '" + existing.get().getName() + "'");
        }

        Valve valve = valveMapper.toEntity(dto, plot);
        return valveMapper.toDTO(valveRepository.save(valve));
    }

    @Override
    public List<ValveDTO> findAll() {
        return valveRepository.findAll().stream().map(valveMapper::toDTO).toList();

    }

    @Override
    public Optional<ValveDTO> findById(Long id) {
        return valveRepository.findById(id).map(valveMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!valveRepository.existsById(id))
            throw new EntityNotFoundException("Valve with id " + id + " not found");

        Valve valve = valveRepository.getReferenceById(id);
        Plot plot = valve.getPlot();
        if (plot != null) {
            plot.setValve(null);
            plotRepository.save(plot);
        }

        valveRepository.deleteById(id);
    }

    @Override
    public ValveDTO update(Long id, ValveDTO dto) {
        if (dto.getId() != null && !dto.getId().equals(id))
            throw new IllegalArgumentException("Valve with given id does not match id path parameter");
        dto.setId(id);
        return save(dto);
    }

    @Override
    @Transactional
    public void changeState(Long id, String command, ActivityType activityType) {
        Valve valve = valveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Valve with id " + id + " not found"));

        if (command != null) {
            if (command.equals("OPEN") && valve.getStatus() == ValveStatus.CLOSED) {
                valve.setStatus(ValveStatus.OPEN);
                log.info("Toggling valve: {} to status {}", valve.getName(), valve.getStatus());
                updateValve(activityType, valve);
            } else if (command.equals("CLOSE") && valve.getStatus() == ValveStatus.OPEN) {
                valve.setStatus(ValveStatus.CLOSED);
                log.info("Toggling valve: {} to status {}", valve.getName(), valve.getStatus());
                updateValve(activityType, valve);
            }
        }
    }

    @Override
    public ValveDTO toggle(Long id, ActivityType type) {
        Valve valve = valveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Valve with id " + id + " not found"));
        log.info("Toggling valve: {}", valve.getName());

        valve.setStatus(valve.getStatus().toggle());

        Valve saved = updateValve(type, valve);

        return valveMapper.toDTO(saved);
    }

    private @NotNull Valve updateValve(ActivityType type, Valve valve) {
        Valve saved = valveRepository.save(valve);
        notifyValve(valve);
        _simNotifySensors(valve);
        createActivity(type, saved);
        return saved;
    }

    private void createActivity(ActivityType activityType, Valve valve) {
        activityService.create(valve, valve.getStatus(), activityType);
    }

    private void notifyValve(Valve valve) {
        String topic = String.format("valve/%s/switch/valve_control/command", valve.getName());
        String payload = (valve.getStatus() == ValveStatus.OPEN) ? "ON" : "OFF";
        mqttPublisher.publish(topic, payload);
    }

    private void _simNotifySensors(Valve valve) {
        Plot plot = valve.getPlot();
        for (Sensor sensor : plot.getSensors()) {
            String simTopic = mqttProperties.getSimTopic() + sensor.getName();
            String simPayload = (valve.getStatus() == ValveStatus.OPEN) ? "ON" : "OFF";
            mqttPublisher.publish(simTopic, simPayload);
        }
    }
}
