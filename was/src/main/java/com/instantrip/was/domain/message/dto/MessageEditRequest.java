package com.instantrip.was.domain.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter @Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
public class MessageEditRequest {

    @Schema(required = false)
    Integer duration;
    @Schema(required = false)
    String contents;
    @Schema(required = false)
    String messageType;
}
