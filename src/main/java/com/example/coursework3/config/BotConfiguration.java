package com.example.coursework3.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfiguration {
    @Value("${bot.name}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

}
