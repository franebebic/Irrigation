package com.fb.irrigation.service;

import com.fb.irrigation.dto.ValveDTO;
import com.fb.irrigation.model.ActivityType;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ValveService {
    ValveDTO save(@Valid ValveDTO dto);
    List<ValveDTO> findAll();
    Optional<ValveDTO> findById(Long id);
    void deleteById(Long id);
    ValveDTO update(Long id, @Valid ValveDTO dto);
    void changeState(Long id, String command, ActivityType manual);
    ValveDTO toggle(Long id, ActivityType manual);

}
