package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.service.CallbackData;
import com.github.mirafi.homemedkit.service.LocaleResourcesProvider;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class MainMenuCommand implements Command {

    private final StateProvider stateProvider;
    private final LocaleResourcesProvider localeResourcesProvider;

    public MainMenuCommand(StateProvider stateProvider, LocaleResourcesProvider localeResourcesProvider) {
        this.stateProvider = stateProvider;
        this.localeResourcesProvider = localeResourcesProvider;
    }


    @Override
    public boolean needsReaction(Update update) {
        return update.hasCallbackQuery() && StateProvider.State.MAIN_MENU.equals(stateProvider.getState(update.getCallbackQuery().getMessage().getChatId()));
    }

    @Override
    public void execute(Update update) {
        CallbackData data = CallbackData.getByNameOrDefault(update.getCallbackQuery().getData());

        switch (data) {
            case SAVE -> stateProvider.updateState(update.getCallbackQuery().getMessage().getChatId(), StateProvider.State.SAVE);
            default -> {}
        }
    }

    @Override
    public boolean isDisplayed(Update update) {
        Long chatId;
        if (update.hasMessage()) {

        }
        return false;
    }

    @Override
    public BotApiMethod<Message> display(Update update) {
        return null;
    }
}
