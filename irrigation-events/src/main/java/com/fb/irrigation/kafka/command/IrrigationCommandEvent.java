package com.fb.irrigation.kafka.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IrrigationCommandEvent {
    private Long plotId;
    private Long valveId;
    private String command; // npr. "OPEN" ili "CLOSE"
    private String reason;  // slobodno tekstualno, npr. "Auto decision due to low moisture"
}
