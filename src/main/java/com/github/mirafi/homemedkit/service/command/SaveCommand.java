package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.dao.AvailableDrugRepository;
import com.github.mirafi.homemedkit.dao.DrugRepository;
import com.github.mirafi.homemedkit.dao.entity.AvailableDrug;
import com.github.mirafi.homemedkit.dao.entity.Drug;
import com.github.mirafi.homemedkit.exception.TelegramServerException;
import com.github.mirafi.homemedkit.service.LocaleResourcesProvider;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class SaveCommand implements Command {
    private final DrugRepository drugRepository;
    private final AvailableDrugRepository availableDrugRepository;
    private final StateProvider stateProvider;
    private final LocaleResourcesProvider localeResourcesProvider;

    public SaveCommand(DrugRepository drugRepository, AvailableDrugRepository availableDrugRepository, StateProvider stateProvider, LocaleResourcesProvider localeResourcesProvider) {
        this.drugRepository = drugRepository;
        this.availableDrugRepository = availableDrugRepository;
        this.stateProvider = stateProvider;
        this.localeResourcesProvider = localeResourcesProvider;
    }

    @Override
    public boolean needsReaction(Update update) {
        /*return update.hasMessage()
                && StateProvider.State.SAVE.equals(stateProvider.getState(update.getMessage().getChatId()))
                && update.getMessage().hasText();*/
        return true;
    }

    @Override
    @Transactional
    public BotApiMethod<Message> execute(Update update) {
        Message message = update.getMessage();
        String[] data = message.getText().split("\n");
        if (data.length < 2) {
            throw new TelegramServerException("save.data.not_found", "Not enough data for save");
        }
        String name = data[0].trim();
        Optional<Drug> drugO = drugRepository.findDrugByNameLikeIgnoreCase(name);
        Drug drug;
        if (drugO.isPresent()) {
            drug = drugO.get();
        } else {
            Drug.DrugBuilder builder = Drug.builder();
            builder.name(data[0]);
            if (data.length >= 3) {
                builder.description(data[1]);
            }
            drug = builder.build();
        }
        String dateStr = data[data.length - 1].trim();
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        drug.getAvailableDrugs().add(new AvailableDrug(message.getChatId(), date));
        drugRepository.save(drug);
        stateProvider.updateState(message.getChatId(), StateProvider.State.MAIN_MENU);
        return SendMessage.builder().chatId(message.getChatId().toString()).text(localeResourcesProvider.getMessage("save.ok")).build();
    }
}
