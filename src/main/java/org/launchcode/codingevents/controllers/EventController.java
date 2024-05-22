package org.launchcode.codingevents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String events(Model model) {
        List<String> events = new ArrayList<>();
        events.add("Devs Meetup");
        events.add("Code 2gether");
        events.add("WebDev Workshop");
        model.addAttribute("events", events);
        return "events/index";
    }
}
