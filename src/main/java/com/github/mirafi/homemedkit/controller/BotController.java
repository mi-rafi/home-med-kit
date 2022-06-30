package com.github.mirafi.homemedkit.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;
import java.util.Scanner;

@Component
public class BotController extends TelegramLongPollingBot {

    private final String username;
    private final String token;

    public BotController(@Value("${telegram.bot.username}") String username, @Value("${telegram.bot.token}") String token) {
        this.username = username;
        this.token = token;
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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage.SendMessageBuilder sendMessageBuilder = SendMessage.builder().chatId(message.getChatId().toString());
            if (message.getText().trim().toLowerCase(Locale.ROOT).equals("ping")) {
                sendMessageBuilder.text("Pong");
            } else {
                sendMessageBuilder.text("Echo! " + message.getText());
            }
            execute(sendMessageBuilder.build());
        }
    }


}
