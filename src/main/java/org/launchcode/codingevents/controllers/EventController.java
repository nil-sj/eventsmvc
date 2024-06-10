package org.launchcode.codingevents.controllers;

import jakarta.validation.Valid;
import org.launchcode.codingevents.data.EventCategoryRepository;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.data.TagRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventCategory;
import org.launchcode.codingevents.models.Tag;
import org.launchcode.codingevents.models.dto.EventTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping
    public String events(@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) Integer tagId, Model model) {
        if (categoryId == null && tagId == null) {
            model.addAttribute("events", eventRepository.findAll());
            String subTitle = "List of All Events";
            model.addAttribute("subTitle", subTitle);
        } else if (categoryId != null && tagId == null) {
            Optional<EventCategory> result = eventCategoryRepository.findById(categoryId);
            if (result.isEmpty()) {
                model.addAttribute("subTitle","Invalid Category ID: " + categoryId);
            } else {
                EventCategory eventCategory = result.get();
                model.addAttribute("subTitle", "Events in Category: " + eventCategory.getName());
                model.addAttribute("events", eventCategory.getEvents());
            }
        }
        else if (categoryId == null && tagId != null) {
            Optional<Tag> result = tagRepository.findById(tagId);
            if (result.isEmpty()) {
                model.addAttribute("subTitle","Invalid Tag: " + tagId);
            } else {
                Tag tag = result.get();
                model.addAttribute("subTitle", "Events with Tag: " + tag.getName());
                model.addAttribute("events", tag.getEvents());
            }
        }
        String title = "Coding Events - Thymeleaf";
        model.addAttribute("title", title);

        return "events/index";
    }

    @GetMapping("detail")
    public String eventDetails(@RequestParam Integer eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isEmpty()) {
            model.addAttribute("subTitle","Invalid Event ID: " + eventId);
        } else {
            Event event = result.get();
            model.addAttribute("subTitle", "Details for the Event: " + event.getName());
            model.addAttribute("event", event);
            model.addAttribute("tags", event.getTags());
        }
        model.addAttribute("title", "Coding Events - Thymeleaf");
        return "events/detail";
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

    @GetMapping("add-tag")
    public String displayAddTagForm(@RequestParam Integer eventId, Model model) {
        Optional<Event> result = eventRepository.findById(eventId);
        Event event = result.get();
        model.addAttribute("title", "Coding Events - Thymeleaf");
        model.addAttribute("subTitle", "Add Tag to: " + event.getName());
        model.addAttribute("tags", tagRepository.findAll());
        model.addAttribute("event", event);
        EventTagDTO eventTag = new EventTagDTO();
        eventTag.setEvent(event);
        model.addAttribute("eventTag", eventTag);
        return "events/add-tag";
    }

    @PostMapping("add-tag")
    public String processAddTagForm(@ModelAttribute @Valid EventTagDTO eventTag, Errors errors, Model model) {
        if (!errors.hasErrors()) {
            Event event = eventTag.getEvent();
            Tag tag = eventTag.getTag();
            if (!event.getTags().contains(tag)){
                event.addTag(tag);
                eventRepository.save(event);
            }
            return "redirect:detail?eventId=" + event.getId();
        }
        return "redirect:/add-tag";
    }

    @GetMapping("edit/{eventId}")
    public String displayEditForm(Model model, @PathVariable int eventId) {
        Optional<Event> result = eventRepository.findById(eventId);
        if (result.isEmpty()) {
            model.addAttribute("subTitle","Invalid Event ID: " + eventId);
        } else {
            Event eventToEdit = result.get();
            model.addAttribute("subTitle", "Edit the Details for the Event: " + eventToEdit.getName() + " (Event Id: " + eventId + ")");
            model.addAttribute("event", eventToEdit);
            model.addAttribute("eventCategories", eventCategoryRepository.findAll());
            //            model.addAttribute("tags", event.getTags());
        }
        model.addAttribute("title", "Coding Events - Thymeleaf");
        return "events/edit";
    }



    @PostMapping("edit")
    public String processEditForm(@ModelAttribute @Valid Event event, Errors errors, Model model, int id) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Coding Events - Thymeleaf");
            model.addAttribute("subTitle", "Edit the Details for the Event: " + event.getName() + "(Event Id: " + event.getId() + ") - Correct the Errors!");
            model.addAttribute("eventCategories", eventCategoryRepository.findAll());
            model.addAttribute("event", event);
            return "redirect:/events/edit/" + id;
        }
        Optional<Event> result = eventRepository.findById(id);
        if (result.isEmpty()) {
            eventRepository.save(event);
            return "redirect:/events";
        } else {
            Event editedEvent = result.get();
            editedEvent.setName(event.getName());
            editedEvent.setEventCategory(event.getEventCategory());
            editedEvent.setEventDetails(event.getEventDetails());
            eventRepository.save(editedEvent);
            model.addAttribute("event", editedEvent);
        }
        return "redirect:/events/detail?eventId=" + id;
    }

}
