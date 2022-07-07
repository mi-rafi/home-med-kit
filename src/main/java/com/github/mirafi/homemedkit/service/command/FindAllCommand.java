package com.github.mirafi.homemedkit.service.command;

import com.github.mirafi.homemedkit.dao.AvailableDrugRepository;
import com.github.mirafi.homemedkit.dao.entity.AvailableDrug;
import com.github.mirafi.homemedkit.dao.entity.Drug;
import com.github.mirafi.homemedkit.service.LocaleResourcesProvider;
import com.github.mirafi.homemedkit.service.StateProvider;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FindAllCommand implements Command {

    private final StateProvider stateProvider;
    private final LocaleResourcesProvider localeResourcesProvider;
    private final AvailableDrugRepository availableDrugRepository;

    public FindAllCommand(StateProvider stateProvider, LocaleResourcesProvider localeResourcesProvider, AvailableDrugRepository availableDrugRepository) {
        this.stateProvider = stateProvider;
        this.localeResourcesProvider = localeResourcesProvider;
        this.availableDrugRepository = availableDrugRepository;
    }

    @Override
    public boolean needsReaction(Update update) {
        return update.hasCallbackQuery() && StateProvider.State.FIND_ALL.equals(stateProvider.getState(update.getCallbackQuery().getMessage().getChatId()));
    }

    @Override
    public BotApiMethod<Message> execute(Update update) {
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        List<AvailableDrug> availableDrugList = availableDrugRepository.findAllByChatId(chatId);
        String message = localeResourcesProvider.getMessage("all.drug.format");
        SendMessage.SendMessageBuilder sendMessageBuilder = SendMessage.builder().chatId(chatId.toString());
        if (availableDrugList.isEmpty()) {
            sendMessageBuilder.text(localeResourcesProvider.getMessage("all.drug.not.found"));
        } else {
            Map<Long, String> drugIdToAvailableDrug = availableDrugList.stream().collect(Collectors.toMap(a -> a.getDrug().getId(), a -> DateTimeFormatter.ISO_LOCAL_DATE.format(a.getExpirationDate()), (a, b) -> a + ", " + b));
            List<Drug> drug = availableDrugList.stream().map(AvailableDrug::getDrug).distinct().toList();
            String msg = drug.stream().map(d -> String.format(message, d.getName(), d.getDescription(), drugIdToAvailableDrug.get(d.getId()))).collect(Collectors.joining("\n"));
            sendMessageBuilder.text(msg);
        }
        stateProvider.updateState(chatId, StateProvider.State.MAIN_MENU);
        return sendMessageBuilder.build();
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
