package com.example.it210_25042026.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @PostMapping("/set-owner")
    public String setOwner(@RequestParam String owner, HttpSession session, RedirectAttributes redirectAttributes) {
        if (owner == null || owner.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "error.name.required");
            return "redirect:/";
        }
        session.setAttribute("ownerName", owner);
        return "redirect:/todo/list";
    }
}