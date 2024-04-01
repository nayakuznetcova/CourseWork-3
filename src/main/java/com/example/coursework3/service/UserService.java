package com.example.coursework3.service;

import com.example.coursework3.entity.Users;
import com.example.coursework3.repository.MessageRepository;
import com.example.coursework3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createNewUser(Update update, Long chatId) {
        if (!userRepository.existsById(chatId)) {
            Users users = new Users();
            users.setName(update.getMessage().getChat().getFirstName());
            users.setChatId(chatId);
            userRepository.save(users);
        }
    }
}
