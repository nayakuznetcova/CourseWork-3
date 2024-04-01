package com.example.coursework3.service;

import com.example.coursework3.entity.Message;
import com.example.coursework3.entity.Users;
import com.example.coursework3.repository.MessageRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RemindService {
    private final MessageRepository messageRepository;

    public Message createNewRemind(String text, LocalDateTime data, Users users) {
        Message message = new Message();
        message.setMessage(text);
        message.setData(data);
        message.setUser(users);
        return messageRepository.save(message);
    }
}
