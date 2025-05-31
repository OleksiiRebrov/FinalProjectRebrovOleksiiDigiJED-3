package org.example.task2_rebrov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login.html";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard.html";
    }

    @GetMapping("/privacy")
    public String privacy() {
        return "privacy.html";
    }

    @GetMapping("/terms")
    public String terms() {
        return "terms.html";
    }
}