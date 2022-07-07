package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.exception.TelegramServerException;
import com.github.mirafi.homemedkit.service.CallbackData;
import com.github.mirafi.homemedkit.service.LocaleResourcesProvider;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
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
    public BotApiMethod<Message> execute(Update update) {
        CallbackData data = CallbackData.getByNameOrDefault(update.getCallbackQuery().getData());

        switch (data) {
            case SAVE -> stateProvider.updateState(update.getCallbackQuery().getMessage().getChatId(), StateProvider.State.SAVE);
            case FIND_ALL -> stateProvider.updateState(update.getCallbackQuery().getMessage().getChatId(), StateProvider.State.FIND_ALL);
            case DELETE -> stateProvider.updateState(update.getCallbackQuery().getMessage().getChatId(), StateProvider.State.DELETE);
            default -> {}
        }
        return null;
    }

    @Override
    public boolean isDisplayed(Update update) {
        Long chatId;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new TelegramServerException("");
        }
        return StateProvider.State.MAIN_MENU.equals(stateProvider.getState(chatId));
    }

    @Override
    public BotApiMethod<Message> display(Update update) {
        Long chatId;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            throw new TelegramServerException("");
        }
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline1.add(InlineKeyboardButton.builder().text(localeResourcesProvider.getMessage("save.drug")).callbackData(CallbackData.SAVE.getName()).build());
        rowInline1.add(InlineKeyboardButton.builder().text(localeResourcesProvider.getMessage("delete.drug")).callbackData(CallbackData.DELETE.getName()).build());
        rowInline2.add(InlineKeyboardButton.builder().text(localeResourcesProvider.getMessage("all.drug")).callbackData(CallbackData.FIND_ALL.getName()).build());
        rowsInline.add(rowInline1);
        rowsInline.add(rowInline2);
        markupInline.setKeyboard(rowsInline);
        return SendMessage.builder().chatId(chatId.toString()).text(localeResourcesProvider.getMessage("main.menu.message")).replyMarkup(markupInline).build();
    }
}
