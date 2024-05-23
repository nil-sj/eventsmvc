package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("events")
public class EventController {

    private static List<Event> events = new ArrayList<>();
    @GetMapping
    public String events(Model model) {
        model.addAttribute("events", events);
        String title = "Coding Events - Thymeleaf";
        model.addAttribute("title", title);
        return "events/index";
    }

    @GetMapping("create")
    public String eventCreate(Model model) {
        String title = "Coding Events - Thymeleaf";
        model.addAttribute("title", title);
        return "events/create";
    }

    @PostMapping("create")
    public String addEvent(@RequestParam String eventName, @RequestParam String eventDescription) {
        events.add(new Event(eventName, eventDescription));
        return "redirect:/events";
    }
}
