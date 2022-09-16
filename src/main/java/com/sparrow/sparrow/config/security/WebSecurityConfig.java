package com.sparrow.sparrow.config.security;

import com.sparrow.sparrow.config.oauth.CustomOAuth2UserService;
import com.sparrow.sparrow.config.oauth.OAuth2AuthenticationSuccessHandler;
import com.sparrow.sparrow.domain.user.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http
                    .cors()
                .and()// cors는 따로 설정했으므로 기본으로만 설정
                    .csrf()
                    .disable()
                    .httpBasic()// 토큰을 사용하기 때문에 basic 인증 disable
                    .disable()
                    .sessionManagement() // 세션 기반이 아님을 선언한다.
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
//                    .antMatchers("/api/v1/**").authenticated()
                    .antMatchers("/", "/api/v1/no-login/**", "/api/v1/auth/**").permitAll()
                    .anyRequest() // /와 /auth/** 이외의 모든 경로는 인증 해야됨
                        .authenticated()
//                .and()
//                    .logout()
//                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
        http.addFilterAfter(
                jwtAuthenticationFilter, // jwtAuthenticationFilter를
                CorsFilter.class // CorsFilter 클래스의 동작이 끝나면 실행하라.
        );
        return http.build();
    }
}
