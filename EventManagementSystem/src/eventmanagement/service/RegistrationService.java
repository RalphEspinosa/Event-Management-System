/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.service;

import eventmanagement.dao.AttendeeDao;
import eventmanagement.dao.EventDao;
import eventmanagement.dao.WaitlistDao;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Attendee;
import eventmanagement.model.Event;
import java.util.List;


public class RegistrationService {
    private EventDao eventDao = new EventDao();
    private AttendeeDao attendeeDao = new AttendeeDao();
    private WaitlistDao waitlistDao = new WaitlistDao();
    
    private static final int defPriority = 5;
    
    // Rebuilds this event's priority queue from the database
    private WaitingEntry.WaitlistManager loadWaitlist(int eventId){
        WaitingEntry.WaitlistManager manager = new WaitingEntry.WaitlistManager();
        waitlistDao.loadInto(eventId, manager);
        return manager;
    }
    private String addToWaitlist(Attendee attendee,int priority){
        if(priority < 1){
            return "Priority must be 1 or higher.";
        }
        if (waitlistDao.isOnWaitlist(attendee.getEventId(), attendee.getEmail())) {
            return "This email is already on the waiting list.";
        }
        if (!waitlistDao.addToWaitlist(attendee, priority)) {
            return "Could not add to the waiting list.";
        }
        return "Added to the waiting list.";
    }
    //Register Attendee
    public String register(Attendee attendee){
        Event event = eventDao.getEventById(attendee.getEventId());
        
        if(event == null){
            return "Event not found";
        }
        if (isAlreadyRegistered(event, attendee.getEmail())){
            return "This email is already registered for this  event";
        }
        if(event.isFull()){
            return "Event is full."  + addToWaitlist(attendee, defPriority);
        }
        attendeeDao.addAttendee(attendee);
        return "Registered successfully.";
    }
    //Join waiting list
    public String joinWaitlist(Attendee attendee, int priority){
        Event event = eventDao.getEventById(attendee.getEventId());
        if(event == null){
            return "Event not found";
        }
        if (isAlreadyRegistered(event, attendee.getEmail())){
            return "This email is already registered for this  event";
        }
        if(event.isFull()){
            return "Event is full."  + addToWaitlist(attendee, defPriority);
        }
        return addToWaitlist(attendee, priority);
    }
    //Admit next
     public String admitNext(int eventId) {
        Event event = eventDao.getEventById(eventId);
        if (event == null) {
            return "Event not found.";
        }
        if (event.isFull()) {
            return "The event is still full.";
        }
        Attendee next = loadWaitlist(eventId).admitNext();
        if (next == null) {
            return "The waiting list is empty.";
        }
        attendeeDao.addAttendee(next);                                  
        waitlistDao.removeFromWaitlist(eventId, next.getEmail());       
        return next.getName() + " has been admitted.";
    }
    public List<WaitingEntry> getWaitlist(int eventId) {
        return loadWaitlist(eventId).getWaitingList();
    }
    //Remove Selected
    public boolean removeFromWaitlist(int eventId, WaitingEntry entry) {
        return waitlistDao.removeFromWaitlist(eventId, entry.getAttendee().getEmail());
    }
    private boolean isAlreadyRegistered(Event event, String email) {
        for (Attendee a : event.getAttendees()) {
            if (a.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }
}
