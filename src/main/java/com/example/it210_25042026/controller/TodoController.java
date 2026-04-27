package com.example.it210_25042026.controller;

import com.example.it210_25042026.model.entity.Priority;
import com.example.it210_25042026.model.entity.Status;
import com.example.it210_25042026.model.entity.Todo;
import com.example.it210_25042026.repository.TodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/todo")
public class TodoController {
    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/list")
    public String list(Model model, HttpSession session,RedirectAttributes redirectAttributes) {
        String owner = (String) session.getAttribute("ownerName");
        if (owner == null) {
            redirectAttributes.addFlashAttribute("error", "error.login.required");
            return "redirect:/";
        }
        model.addAttribute("todos",todoRepository.findAll());
        model.addAttribute("ownerName", owner);
        return "list";
    }


    @GetMapping("/create")
    public String create(Model model) {
        Todo todo = new Todo();
        todo.setStatus(Status.PENDING);
        todo.setPriority(Priority.LOW);
        model.addAttribute("todo", todo);
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", Status.values());
        return "form";
    }
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("todo") Todo todo,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("todo", todo);
            model.addAttribute("priorities", Priority.values());
            model.addAttribute("statuses", Status.values());
            return "form";
        }
        todoRepository.save(todo);
        redirectAttributes.addFlashAttribute("message", "message.save.success");
        return "redirect:/todo/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model,RedirectAttributes redirectAttributes) {
        Optional<Todo> optional = todoRepository.findById(id);

        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "error.id.notfound");
            return "redirect:/todo/list";
        }
        model.addAttribute("todo", optional.get());
        model.addAttribute("priorities", Priority.values());
        model.addAttribute("statuses", Status.values());
        return "form";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!todoRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("message", "error.id.notfound");
            return "redirect:/todo/list";
        }

        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "message.delete.success");
        return "redirect:/todo/list";
    }
}