package com.instantrip.was.domain.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
public class NearbyMessageRequest {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Double latitude;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Double longitude;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer radius;
}
