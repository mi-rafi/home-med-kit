package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.dao.AvailableDrugRepository;
import com.github.mirafi.homemedkit.dao.DrugRepository;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DeleteCommand implements Command{
    private final DrugRepository medKitRepository;
    private final AvailableDrugRepository availableDrugRepository;
    private final StateProvider stateProvider;

    public DeleteCommand(DrugRepository medKitRepository, AvailableDrugRepository availableDrugRepository, StateProvider stateProvider) {
        this.medKitRepository = medKitRepository;
        this.availableDrugRepository = availableDrugRepository;
        this.stateProvider = stateProvider;
    }


    @Override
    public boolean needsReaction(Update update) {
        return update.hasMessage()
                && StateProvider.State.DELETE.equals(stateProvider.getState(update.getMessage().getChatId()))
                && update.getMessage().hasText();
    }

    @Override
    @Transactional
    public void execute(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
//            if (message.hasText()) medKitRepository.findDrugByNameLikeIgnoreCase();
        } else {
        }
        Message message = update.getMessage();
    }

    @Override
    public boolean isDisplayed(Update update) {
        return false;
    }

    @Override
    public BotApiMethod<Message> display(Update update) {
        return null;
    }
}
