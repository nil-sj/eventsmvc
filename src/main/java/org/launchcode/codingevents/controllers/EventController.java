package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @GetMapping
    public String events(Model model) {
        model.addAttribute("events", eventRepository.findAll());
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
        model.addAttribute("eventCategories", eventCategoryRepository.findAll());
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
        eventRepository.save(newEvent);
        return "redirect:/events";
    }

    @GetMapping("delete")
    public String deleteEvent(Model model) {
        model.addAttribute("title", "Coding Events - Thymeleaf");
        model.addAttribute("subTitle", "Form: Delete Event(s)");
        model.addAttribute("events", eventRepository.findAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String removeEvent(@RequestParam(required = false) int[] eventIds) {
        if (eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
            }
        }
        return "redirect:/events";
    }

//    @GetMapping("edit/{eventId}")
//    public String displayEditForm(Model model, @PathVariable int eventId) {
//        Event eventToEdit = EventData.getById(eventId);
//        model.addAttribute("event", eventToEdit);
//        String subTitle = "Edit Event " + eventToEdit.getName() + " (id=" + eventToEdit.getId() +  ")";
//        model.addAttribute("title", "Coding Events - Thymeleaf");
//        model.addAttribute("subTitle", subTitle);
//        model.addAttribute("types", EventType.values());
//        return "events/edit";
//    }
//
//    @PostMapping("edit")
//    public String processEditForm(int eventId, String name, EventType type, String description, String location, boolean shouldRegister, int numberOfAttendees, String contactEmail) {
//        Event eventToEdit = EventData.getById(eventId);
//        eventToEdit.setName(name);
//        eventToEdit.setType(type);
//        eventToEdit.setDescription(description);
//        eventToEdit.setLocation(location);
//        eventToEdit.setShouldRegister(shouldRegister);
//        eventToEdit.setNumberOfAttendees(numberOfAttendees);
//        eventToEdit.setContactEmail(contactEmail);
//        return "redirect:/events";
//    }
}
