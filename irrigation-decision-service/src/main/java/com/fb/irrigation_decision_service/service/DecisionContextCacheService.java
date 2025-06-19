package com.fb.irrigation_decision_service.service;

import com.fb.irrigation.kafka.event.DecisionContextDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class DecisionContextCacheService {

    private final Map<Long, DecisionContextDTO> contextByPlotId = new ConcurrentHashMap<>();

    public void updateContext(DecisionContextDTO context) {
        log.info("🧠 Updating context for plotId={}", context.getPlotId());
        contextByPlotId.put(context.getPlotId(), context);
    }

    public DecisionContextDTO getContextForPlot(Long plotId) {
        return contextByPlotId.get(plotId);
    }

    public boolean hasContextForPlot(Long plotId) {
        return contextByPlotId.containsKey(plotId);
    }
}
