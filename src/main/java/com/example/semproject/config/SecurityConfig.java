package com.example.semproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.debug("Starting security configuration");
        http
            .csrf().disable() // Disable CSRF for simplicity (not recommended for production)
            .authorizeRequests()
            .requestMatchers("/", "/user/**", "/otp/**", "/home", "/login/**", "/css/**", "/js/**", "/images/**").permitAll() // Allow access to homepage and static resources
            .anyRequest().authenticated() // Require authentication for all other endpoints
            .and()
            .formLogin().disable(); // Disable form-based login
        logger.debug("Security configuration completed");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("PasswordEncoder bean created");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        logger.debug("Configuring CORS filter");
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("https://<your-frontend-domain>.github.io"); // Replace with your frontend's GitHub Pages URL
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }
}
