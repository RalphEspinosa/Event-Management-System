package eventmanagement.model;

import eventmanagement.ds.WaitingEntry;
import java.util.LinkedList;
import java.util.PriorityQueue;


public class Event {
    private int eventId;
    private String name;
    private String date;
    private String category;
    private int capacity;
    private LinkedList<Attendee> attendees;
    
    
     //Event constructor ☜(ﾟヮﾟ☜)
    public Event(int eventId, String name, String date, int capacity, String category){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
        this.category = category;
        this.attendees = new LinkedList<>();

    }
     //Setters and getters ᕦ(ò_óˇ)ᕤ
    public int getEventId(){return eventId;}
    public String getName(){return name;}
    public String getDate(){return date;}
    public String getCategory(){return category;}
    public int getCapacity(){return capacity;}
    public LinkedList <Attendee> getAttendees() {return attendees;}

    public void setName(String name){this.name = name;}
    public void setDate(String date){this.date = date;}
   
    public void setCapacity(int capacity){this.capacity = capacity;}
    public void setCategory(String category){this.category = category;}
    public void addAttendee(Attendee attendee){
        attendees.add(attendee);
    }
    //Registration capacity checking 
    public boolean isFull(){
        return attendees.size() >= capacity;
    }
    @Override
    public String toString(){
        return "Event #" + eventId + ": " + name + " on " + date + 
           " (" + attendees.size() + " attendees)";  
    }

    public Object getWaitingList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
        return "Event #" + eventId + ": " + name + " on " + date +
       " [" + category + "] (" + attendees.size() + "/" + capacity + " attendees)";
    } 
}
