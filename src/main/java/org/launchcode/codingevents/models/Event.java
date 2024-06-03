package org.launchcode.codingevents.models;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends AbstractEntity{

    @NotBlank(message="Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @ManyToOne
    @NotNull(message = "Event category is required.")
    private EventCategory eventCategory;
    @OneToOne(cascade = CascadeType.ALL)
    @Valid
    @NotNull
    private EventDetails eventDetails;
    @ManyToMany
    private final List<Tag> tags = new ArrayList<>();

    public Event() {}

    public Event(String name, EventCategory eventCategory) {
        this.name = name;
        this.eventCategory = eventCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventCategory getEventCategory() { return eventCategory; }

    public void setEventCategory(EventCategory eventCategory) { this.eventCategory = eventCategory; }

    public EventDetails getEventDetails() { return eventDetails; }

    public void setEventDetails(EventDetails eventDetails) { this.eventDetails = eventDetails; }

    public List<Tag> getTags() { return tags; }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    @Override
    public String toString() {
        return name;
    }

}
