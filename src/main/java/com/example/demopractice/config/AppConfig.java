package com.example.demopractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class AppConfig {

    @Bean
    public BigDecimal minimumBalance() {
        return BigDecimal.valueOf(500);
    }
}
