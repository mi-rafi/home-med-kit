package com.github.mirafi.homemedkit.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class StateProvider {

    private final ConcurrentMap<Long, State> chatIdToState;

    public StateProvider() {
        this.chatIdToState = new ConcurrentHashMap<>();
    }

    public State getState(Long chatId) {
        return chatIdToState.getOrDefault(chatId, State.HELP);
    }

    public void updateState(Long chatId, State newState) {
        chatIdToState.put(chatId, newState);
    }

    public enum State {
        HELP,
        MAIN_MENU,
        SAVE,
        DELETE,
        FIND_BY_NAME
    }
}
