package com.instantrip.was.domain.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
public class MessageEditRequest {

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer duration;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String contents;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String messageType;
}
