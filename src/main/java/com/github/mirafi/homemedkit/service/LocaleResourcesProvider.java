package com.github.mirafi.homemedkit.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class LocaleResourcesProvider {

    private final Map<Locale, ResourceBundle> resourceBundleMap;
    private final Locale defaultLocale;

    public LocaleResourcesProvider(List<ResourceBundle> resourceBundleList, Locale defaultLocale) {
        resourceBundleMap = resourceBundleList.stream().collect(Collectors.toMap(ResourceBundle::getLocale, r -> r, (a, b) -> a));
        this.defaultLocale = defaultLocale;
    }

    public String getMessage(String key) {
        return getMessage(key, defaultLocale);
    }

    public String getMessage(String key, Locale locale) {
        return resourceBundleMap.get(locale).getString(key);
    }
}
