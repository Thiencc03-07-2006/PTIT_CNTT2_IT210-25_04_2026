package com.example.it210_25042026.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Table(name = "todos")
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.todo.content}")
    private String content;

    @NotNull(message = "{NotNull.todo.dueDate}")
    @FutureOrPresent(message = "{FutureOrPresent.todo.dueDate}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotNull(message = "{NotNull.todo.status}")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "{NotNull.todo.priority}")
    @Enumerated(EnumType.STRING)
    private Priority priority;
}