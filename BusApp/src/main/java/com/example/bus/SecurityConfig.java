package com.example.bus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @SuppressWarnings("deprecation")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(requests -> requests
                .requestMatchers("/login", "/register").permitAll() // Engedélyezett útvonalak
                .anyRequest().authenticated()) // Minden más útvonal autentikálást igényel
            .formLogin(login -> login
                .loginPage("/login") // Testreszabott login oldal
                .defaultSuccessUrl("/", true) // Átirányít a kezdőlapra bejelentkezés után
                .permitAll()) // A login oldal mindenki számára elérhető
            .logout(logout -> logout
                .permitAll()); // A logout is mindenki számára elérhető
        return http.build(); // Visszatérés az új típusú konfigurációval
    }
}
