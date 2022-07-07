package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.dao.AvailableDrugRepository;
import com.github.mirafi.homemedkit.dao.DrugRepository;
import com.github.mirafi.homemedkit.dao.entity.AvailableDrug;
import com.github.mirafi.homemedkit.dao.entity.Drug;
import com.github.mirafi.homemedkit.exception.TelegramServerException;
import com.github.mirafi.homemedkit.service.CallbackData;
import com.github.mirafi.homemedkit.service.LocaleResourcesProvider;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DeleteCommand implements Command{
    private final DrugRepository drugRepository;
    private final AvailableDrugRepository availableDrugRepository;
    private final StateProvider stateProvider;
    private final LocaleResourcesProvider localeResourcesProvider;

    public DeleteCommand(DrugRepository drugRepository, AvailableDrugRepository availableDrugRepository, StateProvider stateProvider, LocaleResourcesProvider localeResourcesProvider) {
        this.drugRepository = drugRepository;
        this.availableDrugRepository = availableDrugRepository;
        this.stateProvider = stateProvider;
        this.localeResourcesProvider = localeResourcesProvider;
    }


    @Override
    public boolean needsReaction(Update update) {
        return (update.hasMessage()
                && StateProvider.State.DELETE.equals(stateProvider.getState(update.getMessage().getChatId()))
                && update.getMessage().hasText())
                || update.hasCallbackQuery()
                && (StateProvider.State.DELETE.equals(stateProvider.getState(update.getCallbackQuery().getMessage().getChatId()))
                && CallbackData.BACK.getName().equals(update.getCallbackQuery().getData()));
    }

    @Override
    @Transactional
    public BotApiMethod<Message> execute(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String data = message.getText();
            Long chat_id = message.getChatId();
            data = data.trim();
            availableDrugRepository.deleteAvailableDrugByChatIdAndName(chat_id, data);
            drugRepository.flush();
            stateProvider.updateState(message.getChatId(), StateProvider.State.MAIN_MENU);
            return SendMessage.builder().chatId(message.getChatId().toString()).text(localeResourcesProvider.getMessage("delete.ok")).build();
        } else if (update.hasCallbackQuery() && CallbackData.BACK.getName().equals(update.getCallbackQuery().getData())) {
            stateProvider.updateState(update.getCallbackQuery().getMessage().getChatId(), StateProvider.State.MAIN_MENU);
            return null;
        }
        return null;
    }

    @Override
    public boolean isDisplayed(Update update) {
        return update.hasCallbackQuery() &&
                StateProvider.State.DELETE.equals(stateProvider.getState(update.getCallbackQuery().getMessage().getChatId()));
    }

    @Override
    public BotApiMethod<Message> display(Update update) {
        Long chat_id = update.getCallbackQuery().getMessage().getChatId();
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(InlineKeyboardButton.builder().text(localeResourcesProvider.getMessage("menu.back")).callbackData(CallbackData.BACK.getName()).build());
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return SendMessage.builder().chatId(chat_id.toString()).text(localeResourcesProvider.getMessage("delete.info")).replyMarkup(markupInline).build();
    }
}
