package com.fb.irrigation.service;

import com.fb.irrigation.context.ContextPublisherService;
import com.fb.irrigation.model.Crop;
import com.fb.irrigation.model.Plantation;
import com.fb.irrigation.repository.CropRepository;
import com.fb.irrigation.repository.PlantationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CropServiceImpl implements CropService{
    private final CropRepository cropRepository;
    private final PlantationRepository plantationRepository;
    private final ContextPublisherService contextPublisherService;

    @Override
    @Transactional
    public Crop save(Crop crop) {
        boolean isUpdate = crop.getId() != null;
        Crop savedCrop = cropRepository.save(crop);

        if (isUpdate) {
            // Nađi sve plantaže koje koriste ovaj crop
            List<Plantation> plantations = plantationRepository.findAllByCrop_Id(savedCrop.getId());

            // Za svaki plot objavi kontekst (makni duplikate ako više plantaža dijeli plot)
            plantations.stream()
                    .map(Plantation::getPlot)
                    .distinct()
                    .forEach(contextPublisherService::sendDecisionContext);
        }

        return savedCrop;
    }

    @Override
    public List<Crop> findAll() {
        return cropRepository.findAll();
    }

    @Override
    public Optional<Crop> findById(Long id) {
        return cropRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        if(plantationRepository.existsByCrop_Id(id)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete crop — it is used by plantations.");
        }
        cropRepository.deleteById(id);
    }
}
