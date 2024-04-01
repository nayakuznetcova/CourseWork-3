package com.example.coursework3;

import com.example.coursework3.config.BotConfiguration;
import com.example.coursework3.entity.Users;
import com.example.coursework3.repository.UserRepository;
import com.example.coursework3.service.RemindService;
import com.example.coursework3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfiguration botConfiguration;
    private final UserRepository userRepository;
    private final RemindService remindService;
    private final UserService userService;
    private final String regular = "([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            userService.createNewUser(update, chatId);
            parsingMessage(chatId, text);
        }
    }

    private void parsingMessage(Long chatId, String text) {
        Pattern pattern = Pattern.compile(regular);
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches()) {
            String data = matcher.group(1);
            String remind = matcher.group(3);
            LocalDateTime parse = LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            Users users = userRepository.findById(chatId).get();
            remindService.createNewRemind(remind, parse, users);

            sendMessage(chatId);
        }
    }

    private void sendMessage(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Успешно сохранено");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfiguration.getBotToken();
    }
}
