/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.model;

import java.util.LinkedList;

/**
 *
 * @author reirii
 */
public class Event {
    private int eventId;
    private String name;
    private String date;
    private LinkedList<Attendee> attendees;
    
    
    //Event constructor ☜(ﾟヮﾟ☜)
    public Event(int eventId, String name, String date){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.attendees = new LinkedList<>();
    }
    
    //Setters and getters ᕦ(ò_óˇ)ᕤ
    public int getEventId(){return eventId;}
    public String getName(){return name;}
    public String getDate(){return date;}
    public LinkedList <Attendee> getAttendees() {return attendees;}

    public void setName(String name){this.name = name;}
    public void setDate(String date){this.date = date;}
    
    public void addAttendee(Attendee attendee){
        attendees.add(attendee);
    }
    @Override
    public String toString(){
        return "Event #" + eventId + ": " + name + " on " + date + 
           " (" + attendees.size() + " attendees)";  
    }
   
}
