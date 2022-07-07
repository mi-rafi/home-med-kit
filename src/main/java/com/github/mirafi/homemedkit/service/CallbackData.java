package com.github.mirafi.homemedkit.service;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum CallbackData {
    SAVE("SAVE"),
    BACK("back"),
    UNDEFINED("undefined");


    private final String name;

    CallbackData(String name) {
        this.name = name;
    }

    public static Optional<CallbackData> getByName(String name) {
        return Arrays.stream(values()).filter(c -> c.getName().equals(name)).findAny();
    }

    public static CallbackData getByNameOrDefault(String name) {
        return getByName(name).orElse(UNDEFINED);
    }

}
