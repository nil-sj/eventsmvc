package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index(Model model) {
        String title = "Coding Events - Thymeleaf";
        String subTitle = "Home Page";
        model.addAttribute("title", title);
        model.addAttribute("subTitle", subTitle);
        return "index";
    }
}
