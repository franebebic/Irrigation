package com.fb.irrigation.service;

import com.fb.irrigation.context.ContextPublisherService;
import com.fb.irrigation.dto.ValveDTO;
import com.fb.irrigation.kafka.command.IrrigationCommandType;
import com.fb.irrigation.kafka.event.ValveStatus;
import com.fb.irrigation.mapper.ValveMapper;
import com.fb.irrigation.model.ActivityType;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import com.fb.irrigation.model.Valve;
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
    private final ContextPublisherService contextPublisherService;

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
    public void changeState(Long id, IrrigationCommandType command, ActivityType activityType) {
        Valve valve = valveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Valve with id " + id + " not found"));

        switch (command) {
            case OPEN -> {
                if (valve.getStatus() == ValveStatus.OPEN) {
                    log.info("Command OPEN ignored — valve {} already OPEN", valve.getName());
                    return;
                }
                applyStateChange(valve, ValveStatus.OPEN, activityType);
            }
            case CLOSE -> {
                if (valve.getStatus() == ValveStatus.CLOSED) {
                    log.info("Command CLOSE ignored — valve {} already CLOSED", valve.getName());
                    return;
                }
                applyStateChange(valve, ValveStatus.CLOSED, activityType);
            }
            default -> throw new IllegalArgumentException("Unsupported command: " + command);
        }
    }

    @Override
    public ValveDTO toggle(Long id, ActivityType type) {
        Valve valve = valveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Valve with id " + id + " not found"));
        Valve saved = applyStateChange(valve, valve.getStatus().toggle(), type);
        return valveMapper.toDTO(saved);
    }

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


    private @NotNull Valve applyStateChange(Valve valve, ValveStatus valveStatus, ActivityType activityType) {
        log.info("Switching valve: {} from {} to {}", valve.getName(), valve.getStatus(),valveStatus);
        valve.setStatus(valveStatus);

        Valve saved = valveRepository.save(valve);
        notifyValve(saved);
        notifyIrrigationDecisionService(valve);
        _simNotifySensors(saved);
        createActivity(activityType, saved);
        return saved;
    }

    private void notifyIrrigationDecisionService(Valve valve) {
        Plot plot=valve.getPlot();
        contextPublisherService.sendDecisionContext(plot);
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
