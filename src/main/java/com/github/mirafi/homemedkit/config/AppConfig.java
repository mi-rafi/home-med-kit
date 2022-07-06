package com.github.mirafi.homemedkit.config;

import com.github.mirafi.homemedkit.service.LocaleResourcesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Configuration
public class AppConfig {

    @Bean
    public LocaleResourcesProvider localeResourcesProvider() {
        ResourceBundle en = ResourceBundle.getBundle("messages");
        Locale ruLoc = new Locale("ru", "RU");
        ResourceBundle ru = ResourceBundle.getBundle("messages", ruLoc);
        return new LocaleResourcesProvider(List.of(en, ru), ruLoc);
    }


}
