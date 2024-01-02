package com.instantrip.was.domain.tourapi.controller;

import com.instantrip.was.domain.tourapi.dto.TourApiRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path = "/api/tourapi")

@Slf4j
@Tag(name = "Tour API 연동")
public class TourApiController {
    private final static String endPoint = "http://apis.data.go.kr/B551011/KorService1";

    @Value("${tour.api.encoding-key}")
    private String encodingKey;

    @PostMapping(path = "/location-based")
    public ResponseEntity<String> getLocationBasedList1(@RequestBody TourApiRequest tourApiRequest) {
        log.info("위치기반 API 요청");

        WebClient webClient = WebClient.create();

        StringBuilder uriBuilder = new StringBuilder(endPoint + "/locationBasedList1");
        uriBuilder.append("?MobileOS=" + tourApiRequest.getMobileOS());
        uriBuilder.append("&MobileApp=" + tourApiRequest.getMobileApp());
        uriBuilder.append("&_type=json");
        uriBuilder.append("&mapX=" + tourApiRequest.getMapX());
        uriBuilder.append("&mapY=" + tourApiRequest.getMapY());
        uriBuilder.append("&radius=" + tourApiRequest.getRadius());
        if (!tourApiRequest.getContentType().isEmpty())
            uriBuilder.append("&contentTypeId=" + tourApiRequest.getContentType());
        if (!tourApiRequest.getArrange().isEmpty())
            uriBuilder.append("&arrange=" + tourApiRequest.getArrange());
        uriBuilder.append("&serviceKey=" + encodingKey);

        URI uri = null;
        try {
            uri = new URI(uriBuilder.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        log.info("요청 URI : {} ", uriBuilder.toString());

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        String responseBody = responseEntity.getBody();
        log.info(responseBody);
        return responseEntity;
    }
}
