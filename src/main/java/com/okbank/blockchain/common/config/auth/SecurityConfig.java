package com.okbank.blockchain.common.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
//                .authorizeHttpRequests((authz) -> authz
//                        .anyRequest().authenticated()
//                )
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .httpBasic(withDefaults());
        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().antMatchers("/hello", "/ignore2");
//    }
}
