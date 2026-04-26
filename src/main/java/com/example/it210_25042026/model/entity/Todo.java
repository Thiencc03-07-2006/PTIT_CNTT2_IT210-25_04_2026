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

    @NotBlank(message = "Nội dung không được để trống")
    private String content;

    @NotNull(message = "Ngày không được để trống")
    @FutureOrPresent(message = "Ngày phải là hiện tại hoặc tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotNull(message = "Trạng thái không được để trống")
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull(message = "Độ ưu tiên không được để trống")
    @Enumerated(EnumType.STRING)
    private Priority priority;
}