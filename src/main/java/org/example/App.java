package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class App {

    public static void main(String[] args) {
        try {
            new DatabaseManager();

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new STIW3054_bugbug_bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
