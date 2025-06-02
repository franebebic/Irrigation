package com.fb.irrigation.service;

import com.fb.irrigation.dto.SensorDTO;
import com.fb.irrigation.mapper.SensorMapper;
import com.fb.irrigation.model.Plot;
import com.fb.irrigation.model.Sensor;
import com.fb.irrigation.repository.PlotRepository;
import com.fb.irrigation.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SensorServiceImpl implements SensorService {
    private final SensorMapper sensorMapper;
    private final SensorRepository sensorRepository;
    private final PlotRepository plotRepository;

    @Override
    public SensorDTO save(SensorDTO dto) {
        Plot plot = null;
        if (dto.getPlotId() != null)
            plot = plotRepository.findById(dto.getPlotId()).orElseThrow(() -> new EntityNotFoundException("Plot with id "+dto.getPlotId()+" not found"));

        Sensor sensor = sensorMapper.toEntity(dto, plot);
        return sensorMapper.toDTO(sensorRepository.save(sensor));
    }

    @Override
    public SensorDTO update(Long id, SensorDTO dto) {
        if (dto.getId() != null && !dto.getId().equals(id))
            throw new IllegalArgumentException("DTO id does not match path variable id");
        dto.setId(id);

        if(!sensorRepository.existsById(dto.getId())) {
            throw new EntityNotFoundException("Sensor with id " + id + " not found");
        }
        return save(dto);
    }

    @Override
    public List<SensorDTO> findAll() {
        return sensorRepository.findAll().stream().map(sensorMapper::toDTO).toList();
    }

    @Override
    public Optional<SensorDTO> findById(Long id) {
        return sensorRepository.findById(id).map(sensorMapper::toDTO);
    }

    @Override
    public void deleteById(Long id) {
        if (!sensorRepository.existsById(id))
            throw new EntityNotFoundException("Sensor with id " + id + " not found");
        sensorRepository.deleteById(id);
    }
}
