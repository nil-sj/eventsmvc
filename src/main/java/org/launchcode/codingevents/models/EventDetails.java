package org.launchcode.codingevents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class EventDetails extends AbstractEntity {

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

    @OneToOne(mappedBy = "eventDetails")
    private Event event;

    public EventDetails(String description, String location, boolean shouldRegister, int numberOfAttendees, String contactEmail) {
        this.description = description;
        this.location = location;
        this.shouldRegister = shouldRegister;
        this.numberOfAttendees = numberOfAttendees;
        this.contactEmail = contactEmail;
    }

    public EventDetails() {}

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

}
