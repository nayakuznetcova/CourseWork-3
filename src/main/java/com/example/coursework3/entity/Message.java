package com.example.coursework3.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
