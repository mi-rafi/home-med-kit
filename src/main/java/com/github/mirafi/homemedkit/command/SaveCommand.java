package com.github.mirafi.homemedkit.command;

import com.github.mirafi.homemedkit.dao.AvailableDrugRepository;
import com.github.mirafi.homemedkit.dao.MedKitRepository;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SaveCommand implements Command {
    private final MedKitRepository medKitRepository;
    private final AvailableDrugRepository availableDrugRepository;
    private final StateProvider stateProvider;

    public SaveCommand(MedKitRepository medKitRepository, AvailableDrugRepository availableDrugRepository, StateProvider stateProvider) {
        this.medKitRepository = medKitRepository;
        this.availableDrugRepository = availableDrugRepository;
        this.stateProvider = stateProvider;
    }


    @Override
    public boolean needsReaction(Update update) {
        return update.hasMessage()
                && StateProvider.State.SAVE.equals(stateProvider.getState(update.getMessage().getChatId()))
                && update.getMessage().hasText()
    }

    @Override
    @Transactional
    public Message execute(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) medKitRepository.findDrugByNameLikeIgnoreCase()
        } else {
            return null;
        }
    }
}
