package com.fb.irrigation_decision_service.listener;


import com.fb.irrigation.kafka.command.IrrigationCommandEvent;
import com.fb.irrigation.kafka.event.DecisionContextDTO;
import com.fb.irrigation.kafka.event.MeasurementEvent;
import com.fb.irrigation_decision_service.service.DecisionContextCacheService;
import com.fb.irrigation_decision_service.service.IrrigationDecisionService;
import com.fb.irrigation_decision_service.service.KafkaProducerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class IrrigationEvaluationListener {

    private final DecisionContextCacheService decisionContextCacheService;
    private final IrrigationDecisionService irrigationDecisionService;
    private final KafkaProducerService kafkaProducerService;

    @KafkaListener(
            topics = "${kafka.topics.measurement}",
            groupId = "measurement-consumers",
            properties = {
                    "spring.json.value.default.type=com.fb.irrigation.kafka.event.MeasurementEvent"
            })
    public void handle(MeasurementEvent event) {
        log.info("\uD83C\uDF21\uFE0F Received event : {}", event);
        DecisionContextDTO context = decisionContextCacheService.getContextForPlot(event.getPlotId());

        if (context == null) {
            log.warn("⚠\uFE0F No content for plotId={}, skipping evaluation", event.getPlotId());
        } else {
            log.info("Evaluating measurement for plotID={} using context: {}", event, context);
            if (irrigationDecisionService.shouldIrrigate(event, context)) {
                log.info("\uD83D\uDCA7 IDS decided to irrigate plot {} by turning valve {} ON", context.getPlotName(), context.getValveName());
                IrrigationCommandEvent command = IrrigationCommandEvent.builder()
                        .command("OPEN")
                        .reason("Moisture low on plot " + context.getPlotName())
                        .valveId(context.getValveId())
                        .plotId(context.getPlotId())
                        .build();
                kafkaProducerService.sendIrrigationCommand(command);
            } else {
                IrrigationCommandEvent command = IrrigationCommandEvent.builder()
                        .command("CLOSE")
                        .reason("Moisture high on plot " + context.getPlotName())
                        .valveId(context.getValveId())
                        .plotId(context.getPlotId())
                        .build();
                kafkaProducerService.sendIrrigationCommand(command);
                log.info("⛔ IDS decided NOT to irrigate plot {} yet!", context.getPlotName());
            }
        }
    }

    @KafkaListener(
            topics = "${kafka.topics.decision-context}",
            groupId = "context-consumers",
            properties = {
                    "spring.json.value.default.type=com.fb.irrigation.kafka.event.DecisionContextDTO"
            })
    public void handle(DecisionContextDTO context) {
        log.info("📥 Received context : {}", context);
        decisionContextCacheService.updateContext(context);
    }
}