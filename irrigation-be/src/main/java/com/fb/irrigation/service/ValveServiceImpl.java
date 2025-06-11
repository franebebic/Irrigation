package com.fb.irrigation.service;

import com.fb.irrigation.dto.ValveDTO;
import com.fb.irrigation.mapper.ValveMapper;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Valve;
import com.fb.irrigation.model.ValveStatus;
import com.fb.irrigation.mqtt.MqttPublisher;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.ValveRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ValveServiceImpl implements ValveService {
    private final ValveMapper valveMapper;
    private final ValveRepository valveRepository;
    private final PlotRepository plotRepository;
    private final MqttPublisher mqttPublisher;

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
    public ValveDTO toggle(Long id) {
        Valve valve = valveRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Valve with id " + id + " not found"));

        valve.setStatus(valve.getStatus().toggle());
        Valve saved = valveRepository.save(valve);

        String topic = "valve/esp-valve-1/switch/valve_control/command";
        String payload=(valve.getStatus()==ValveStatus.OPEN)?"ON":"OFF";
        mqttPublisher.publish(topic, payload);

        return valveMapper.toDTO(saved);
    }
}
