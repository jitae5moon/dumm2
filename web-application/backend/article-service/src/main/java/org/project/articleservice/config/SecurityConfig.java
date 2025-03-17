package org.project.articleservice.config;

import lombok.RequiredArgsConstructor;
import org.project.articleservice.security.filter.JwtValidationFilter;
import org.project.articleservice.security.handler.CustomAuthenticationSuccessHandler;
import org.project.articleservice.security.jwt.JwtProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(formLoginConfig ->
                        formLoginConfig
                                .loginPage("/login")
                                .successHandler(customAuthenticationSuccessHandler)
                )
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/css/**", "/js/**", "/favicon.ico").permitAll()
                                .requestMatchers("/login/**", "/error").permitAll()
                                .requestMatchers("/articles").authenticated()
                )
                .addFilterBefore(new JwtValidationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
