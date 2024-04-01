package com.example.coursework3.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Users {
    @Id
    private Long chatId;
    private String name;
}
