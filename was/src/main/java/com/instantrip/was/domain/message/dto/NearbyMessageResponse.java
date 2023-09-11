package com.instantrip.was.domain.message.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@ToString
public class NearbyMessageResponse {
    private Integer count;
    private List<MessageResponse> items;

    public NearbyMessageResponse(List<MessageResponse> items) {
        this.count = items.size();
        this.items = items;
    }
}
