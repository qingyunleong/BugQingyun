package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.sql.*;

public class STIW3054_bugbug_bot extends TelegramLongPollingBot {

    private String loginStaffID, loginPassword, pinSA;
    private String User_ID, ICNO,User_Name, Mobile_TelNo, Email;
    private String School_Admin_ID,Staff_ID,Staff_Name,Office_TelNo,Staff_Mobile_TelNo,Staff_Email,School_Name,Building_Location,Room_ID;
    private String Room_Description,Maximum_Capacity,Room_Type,Status;
    @Override
    public String getBotUsername() {
        return "ttestingg1_bot";
    }

    @Override
    public String getBotToken() {
        return "5913758755:AAGNP8fc-DEW8lGdeFZxiMqachdQZ3iE2F8";
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message message;
        if(update.hasMessage()){
            message = update.getMessage();
        }else
            return;

        String[] com = message.getText().split(" - ");

        String chatId = message.getChatId().toString();
        String command = update.getMessage().getText();
        SendMessage sendMessage = new SendMessage();
        DatabaseManager app = new DatabaseManager();

        switch (com[0]){
            case "/start":
            case "0":
                sendMessage.setText("Hello, "+ update.getMessage().getFrom().getFirstName() + ". \ud83d\udc4b\n" +
                        "Welcome to Meeting Room Booking System!\n\n" +
                        "What is your position ? \n 1. /systemAdmin \n 2. /schooladmin \n 3. /user \n");
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "/systemAdmin":
                sendMessage.setText("Here is a list of available commands:\n" +
                        "/loginSystemAdmin - login to your account\n\n" +
                        "Reply 0: Back to main page");
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "/loginSystemAdmin":
                sendMessage.setText("Please enter your Staff ID: " + "\n(Format: Login Staff ID - xxxxxx)");
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "Login Staff ID":
                loginStaffID = com[1];
                sendMessage.setText("Please enter your Password: " + "\n(Format: Login Password - xxxxxx)");
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "Login Password":
                loginPassword = com[1];
                String login = app.login(loginStaffID,loginPassword);
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                sendMessage.setText(login);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "/approvedSchoolAdmin":
                sendMessage.setText("Please Enter the your PIN number if you want process to approve the school admin. \n" + "Format: PinSA - xxxxxxx");
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "PinSA":
                pinSA = com[1];
                String checkAdminPin = app.checkAdminPin(loginStaffID,pinSA);
                sendMessage.setText(checkAdminPin);
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "Approve":
                Staff_ID = com[1];
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                app.updateStatusSchoolAdmin(Staff_ID);
                sendMessage.setText("Status of " + Staff_ID + " already been approved." +
                        "\n\n/displaySchoolAdmin - display the list of School Admin\n " + "Reply 0: Back to main page");
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            case "/displaySchoolAdmin":
                String displayAdminApproved = app.displayAdminApproved();
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                sendMessage.setText("School Admin: \n\n" + displayAdminApproved);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;

            default:
                sendMessage.setText("Please enter command available with the correct format. \u2764\ufe0f");
                sendMessage.setChatId(chatId);
                sendMessage.setChatId(update.getMessage().getChatId().toString());
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }



}
