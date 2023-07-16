package com.instantrip.was.domain.user.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.instantrip.was.domain.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class KakaoService {
    private Logger logger = LoggerFactory.getLogger(KakaoService.class);

    @Value("${kakao-rest-api-key}")
    private String REST_API_KEY;

    public String getKakaoAccessToken(String code) {
        logger.info("Kakao AccessToken 요청");

        String reqUrl = "https://kauth.kakao.com/oauth/token";
        String accessToken = "";
        String refreshToken = "";

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // 스트림 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + REST_API_KEY);
            sb.append("&redirect_uri=http://localhost:8080/api/users/oauth");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            logger.info("전송 url : {}", sb);

            // 결과 수신
            int responseCode = conn.getResponseCode();
            logger.info("▶▶▶ responseCode : {}", responseCode);
            logger.info("▶▶▶ responseMessage : {}", conn.getResponseMessage());

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            logger.info("▶▶▶ responseData : {}", response);

            // JSON 파싱
            JsonElement jsonElement = JsonParser.parseString(response.toString());

            accessToken = jsonElement.getAsJsonObject().get("access_token").getAsString();
            refreshToken = jsonElement.getAsJsonObject().get("access_token").getAsString();

            logger.info("▶▶▶ accessToken : {}", accessToken);
            logger.info("▶▶▶ refreshToken : {}", refreshToken);

            br.close();
            bw.close();
        } catch (Exception e) {
            logger.error("access token 발급 중 에러 발생");
            logger.error("", e);
        }

        return accessToken;
    }

    public User getKakaoUserInfo(String accessToken) {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";

        long id = 0L;
        String email = null;
        User user = null;

        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 전송
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            // 결과 수신
            int responseCode = conn.getResponseCode();
            logger.info("▶▶▶ responseCode : {}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line = "";
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            logger.info("▶▶▶ responseData : {}", response);

            // JSON 파싱
            JsonElement jsonElement = JsonParser.parseString(response.toString());
            id = jsonElement.getAsJsonObject().get("id").getAsLong();
            boolean hasEmail = jsonElement.getAsJsonObject().get("kakao_account")
                    .getAsJsonObject().get("has_email").getAsBoolean();

            if (hasEmail)
                email = jsonElement.getAsJsonObject().get("kakao_account")
                        .getAsJsonObject().get("email").getAsString();

            logger.info("▶▶▶ id: {}", id);
            logger.info("▶▶▶ email: {}", email);

            user = new User(email, id);

            br.close();
        } catch (Exception e) {
            logger.error("Kakao 사용자 정보 가져오는 중 에러 발생");
            logger.error("", e);
        }

        return user;
    }
}
