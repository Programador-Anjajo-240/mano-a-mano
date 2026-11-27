package com.manoamano.mano_a_mano.comun.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.manoamano.mano_a_mano.usuarios.security.RolSuccessHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RolSuccessHandler successHandler) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/registro", "/confirmar", "/login", "/error", 
                                 "/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                .requestMatchers("/tecnico/**").hasRole("TECNICO")
                .requestMatchers("/cliente/**").hasRole("CLIENTE")             
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.loginPage("/login").successHandler(successHandler).failureUrl("/login?error").permitAll());
        return http.build();
    }
}