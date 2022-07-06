package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.service.StateProvider;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GetAllDrugCommand implements Command {

    private final StateProvider stateProvider;

    public GetAllDrugCommand(StateProvider stateProvider) {
        this.stateProvider = stateProvider;
    }

    @Override
    public boolean needsReaction(Update update) {
        return false;
    }

    @Override
    public BotApiMethod<Message> execute(Update update) {
        return null;
    }
}
