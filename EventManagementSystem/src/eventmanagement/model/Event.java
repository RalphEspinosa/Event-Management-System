package eventmanagement.model;

import eventmanagement.ds.WaitingEntry;

import java.util.LinkedList;
import java.util.PriorityQueue;


public class Event {
    private int eventId;
    private String name;
    private String date;
    private String category = "";
    private LinkedList<Attendee> attendees;
    
     //Event constructor ☜(ﾟヮﾟ☜)
    public Event(int eventId, String name, String date){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.attendees = new LinkedList<>();
    }

    public Event(int aInt, String string, String string0, String string1, int aInt0) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
     //Setters and getters ᕦ(ò_óˇ)ᕤ
    public int getEventId(){return eventId;}
    public String getName(){return name;}
    public String getDate(){return date;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}
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

    public boolean isFull() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getCapacity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}