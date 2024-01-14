package com.instantrip.was.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instantrip.was.domain.auth.JwtAuthenticationFilter;
import com.instantrip.was.domain.auth.JwtProvider;
import com.instantrip.was.domain.member.model.MemberRole;
import com.instantrip.was.global.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/login", "/api/members",
                                        "/v3/api-docs/**",
                                        "/configuration/ui",
                                        "/swagger-resources/**",
                                        "/configuration/security",
                                        "/swagger-ui/**",
                                        "/webjars/**").permitAll()
                                .requestMatchers("/admin/**").hasRole(MemberRole.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig
                                // 인증
                                .authenticationEntryPoint((request, response, authException) -> {
                                    BaseResponse<Void> baseResponse = new BaseResponse<>("401", "Access Denied : Unauthorized.");
                                    response.setStatus(401);
                                    response.setCharacterEncoding("utf-8");
                                    response.setContentType("application/json; charset=UTF-8");
                                    response.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));
                                })
                                // 권한
                                .accessDeniedHandler((request, response, accessDeniedException) -> {
                                    BaseResponse<Void> baseResponse = new BaseResponse<>("403", "Access Denied : No Permission.");
                                    response.setStatus(403);
                                    response.setCharacterEncoding("utf-8");
                                    response.setContentType("application/json; charset=UTF-8");
                                    response.getWriter().write(new ObjectMapper().writeValueAsString(baseResponse));

                                })
                )
                .formLogin((formLogin) ->
                        formLogin
                                .usernameParameter("loginId")
                                .passwordParameter("loginPassword")
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
