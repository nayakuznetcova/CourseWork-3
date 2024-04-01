package com.example.coursework3.repository;

import com.example.coursework3.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByData(LocalDateTime data);
    List<Message> findAllByDataIsBefore(LocalDateTime data);
}
