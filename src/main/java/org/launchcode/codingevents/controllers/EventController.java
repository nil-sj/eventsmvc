package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.models.Event;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("events")
public class EventController {

    @GetMapping
    public String events(Model model) {
        model.addAttribute("events", EventData.getAll());
        String title = "Coding Events - Thymeleaf";
        String subTitle = "List of Events";
        model.addAttribute("title", title);
        model.addAttribute("subTitle", subTitle);
        return "events/index";
    }

    @GetMapping("create")
    public String eventCreate(Model model) {
        String title = "Coding Events - Thymeleaf";
        String subTitle = "Form: Create An Event";
        model.addAttribute("title", title);
        model.addAttribute("subTitle", subTitle);
        model.addAttribute("event", new Event());
        return "events/create";
    }

    @PostMapping("create")
    public String addEvent(@ModelAttribute @Valid Event newEvent, Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Coding Events - Thymeleaf");
            model.addAttribute("subTitle", "Form: Create An Event - Correct the Errors!");
            model.addAttribute("errorMessage", "Bad data! Please correct the following errors:");
            return "events/create";
        }
        EventData.add(newEvent);
        return "redirect:/events";
    }

    @GetMapping("delete")
    public String deleteEvent(Model model) {
        model.addAttribute("title", "Coding Events - Thymeleaf");
        model.addAttribute("subTitle", "Form: Delete Event(s)");
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String removeEvent(@RequestParam(required = false) int[] eventIds) {
        if (eventIds != null) {
            for (int id : eventIds) {
                EventData.remove(id);
            }
        }
        return "redirect:/events";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Event eventToEdit = EventData.getById(eventId);
        model.addAttribute("event", eventToEdit);
        String subTitle = "Edit Event " + eventToEdit.getName() + " (id=" + eventToEdit.getId() +  ")";
        model.addAttribute("title", "Coding Events - Thymeleaf");
        model.addAttribute("subTitle", subTitle);
        return "events/edit";
    }

    @PostMapping("edit")
    public String processEditForm(int eventId, String name, String description, String location, boolean shouldRegister, int numberOfAttendees, String contactEmail) {
        Event eventToEdit = EventData.getById(eventId);
        eventToEdit.setName(name);
        eventToEdit.setDescription(description);
        eventToEdit.setLocation(location);
        eventToEdit.setShouldRegister(shouldRegister);
        eventToEdit.setNumberOfAttendees(numberOfAttendees);
        eventToEdit.setContactEmail(contactEmail);
        return "redirect:/events";
    }
}
