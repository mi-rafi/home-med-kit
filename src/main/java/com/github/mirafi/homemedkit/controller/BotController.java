package com.github.mirafi.homemedkit.controller;

import com.github.mirafi.homemedkit.service.command.Command;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotController extends TelegramLongPollingBot {

    private static final Logger log = LoggerFactory.getLogger(BotController.class);
    private final String username;
    private final String token;

    private final List<Command> commands;

    public BotController(@Value("${telegram.bot.username}") String username, @Value("${telegram.bot.token}") String token, List<Command> commands) {
        this.username = username;
        this.token = token;
        this.commands = commands;
    }


    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        commands.stream().filter(c -> c.needsReaction(update)).forEach(c -> {
            BotApiMethod<Message> method = c.execute(update);
            if (method != null) {
                try {
                    execute(method);
                } catch (TelegramApiException e) {
                    log.error("can not execute command", e);
                }
            }
        });
        commands.stream().filter(c -> c.isDisplayed(update)).forEach(c -> {
            try {
                BotApiMethod<Message> display = c.display(update);
                if (display != null) {
                    execute(display);
                }
            } catch (TelegramApiException e) {
                log.error("can not execute command", e);
            }
        });
    }


    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("ping"));

        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }


}
