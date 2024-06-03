package org.launchcode.codingevents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;

@Entity
public class Event extends AbstractEntity{

    @NotBlank(message="Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
    @ManyToOne
    @NotNull(message = "Event category is required.")
    private EventCategory eventCategory;
    @Size(max = 500, message = "Description too long!")
    private String description;
    @NotBlank(message="Location cannot be left blank.")
    private String location;
    @AssertTrue(message="Attendees must register for the event.")
    private boolean shouldRegister;
    @Positive(message="Number of attendees must be one or more.")
    private int numberOfAttendees;
    @Email(message = "Invalid email. Try again.")
    private String contactEmail;

    public Event() {}

    public Event(String name, String description, String location, boolean shouldRegister, int numberOfAttendees, String contactEmail, EventCategory eventCategory) {
        this.name = name;
        this.eventCategory = eventCategory;
        this.description = description;
        this.location = location;
        this.shouldRegister = shouldRegister;
        this.numberOfAttendees = numberOfAttendees;
        this.contactEmail = contactEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventCategory getEventCategory() { return eventCategory; }

    public void setEventCategory(EventCategory eventCategory) { this.eventCategory = eventCategory; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public boolean isShouldRegister() { return shouldRegister; }

    public void setShouldRegister(boolean shouldRegister) { this.shouldRegister = shouldRegister; }

    public int getNumberOfAttendees() { return numberOfAttendees; }

    public void setNumberOfAttendees(int numberOfAttendees) { this.numberOfAttendees = numberOfAttendees; }

    public String getContactEmail() { return contactEmail; }

    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    @Override
    public String toString() {
        return name;
    }

}
