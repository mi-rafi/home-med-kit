package com.github.mirafi.homemedkit.service.command;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    boolean needsReaction(Update update);

    BotApiMethod<Message> execute(Update update);
}
