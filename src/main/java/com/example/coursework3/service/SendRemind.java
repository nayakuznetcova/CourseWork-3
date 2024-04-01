package com.example.coursework3.service;

import com.example.coursework3.TelegramBot;
import com.example.coursework3.entity.Message;
import com.example.coursework3.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendRemind {
    private final MessageRepository messageRepository;
    private final TelegramBot telegramBot;

    @Scheduled(cron = "0 * * * * *")
    public void searchRemind() {
        List<Message> allByData = messageRepository.findAllByData(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (Message message : allByData) {
            Long chatId = message.getUser().getChatId();
            String text = message.getMessage();
            sendMessage(chatId, text);
            messageRepository.delete(message);
        }
    }

    @Scheduled(cron = "1 1 1 * * *")
    public void searchRemindLater() {
        List<Message> allByData = messageRepository.findAllByDataIsBefore(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        for (Message message : allByData) {
            Long chatId = message.getUser().getChatId();
            String text = message.getMessage();
            sendMessage(chatId, text);
            messageRepository.delete(message);
        }
    }

    public org.telegram.telegrambots.meta.api.objects.Message sendMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            return telegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
