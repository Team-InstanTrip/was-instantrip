package com.instantrip.was.domain.tourapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class TourApiRequest {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "안드로이드: AND")
    String mobileOS;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "모바일 앱 이름")
    String mobileApp;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "정렬구분 (A=제목순, C=수정일순, D=생성일순) 대표이미지가반드시있는정렬(O=제목순, Q=수정일순, R=생성일순)")
    String arrange;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "GPS X좌표(WGS84 경도좌표)")
    String mapX;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "GPS Y좌표(WGS84 위도좌표)")
    String mapY;
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "거리반경(단위:m), Max값 20000m=20Km")
    String radius;
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점)")
    String contentType;
}
