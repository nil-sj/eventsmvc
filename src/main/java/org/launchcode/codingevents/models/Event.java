package org.launchcode.codingevents.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

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

    @Override
    public String toString() {
        return name;
    }

}
