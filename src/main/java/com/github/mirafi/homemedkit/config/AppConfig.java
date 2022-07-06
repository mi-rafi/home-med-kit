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
        ResourceBundle en = ResourceBundle.getBundle("message");
        Locale ruLoc = new Locale("ru");
        ResourceBundle ru = ResourceBundle.getBundle("message", ruLoc);
        return new LocaleResourcesProvider(List.of(en, ru), ruLoc);
    }


}
