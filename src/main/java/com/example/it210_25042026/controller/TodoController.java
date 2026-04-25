package com.example.it210_25042026.controller;

import com.example.it210_25042026.model.entity.Priority;
import com.example.it210_25042026.model.entity.Status;
import com.example.it210_25042026.model.entity.Todo;
import com.example.it210_25042026.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todo")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("todos",todoRepository.findAll());
        return "list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Todo todo = new Todo();
        todo.setStatus(Status.PENDING);
        todo.setPriority(Priority.LOW);
        model.addAttribute("todo",todo);
        model.addAttribute("priorities",Priority.values());
        return "form";
    }

    @PostMapping("/create")
    public String createPost(@Valid @ModelAttribute("todo") Todo todo, BindingResult bindingResult,Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todo",todo);
            model.addAttribute("priorities",Priority.values());
            return "form";
        }
        todoRepository.save(todo);
        return "redirect:/todo/list";
    }
}