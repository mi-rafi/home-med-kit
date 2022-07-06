package com.github.mirafi.homemedkit.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    boolean needsReaction(Update update);

    Message execute(Update update);
}
