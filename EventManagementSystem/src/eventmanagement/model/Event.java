package eventmanagement.model;

import java.util.LinkedList;
import java.util.PriorityQueue;


public class Event {
    private int eventId;
    private String name;
    private String date;
    private String category = "";
    private LinkedList<Attendee> attendees;
    private PriorityQueue<WaitingEntry> waitingList = new PriorityQueue<>();
    
    
    public Event(int eventId, String name, String date){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.attendees = new LinkedList<>();
    }
    
    public int getEventId(){return eventId;}
    public String getName(){return name;}
    public String getDate(){return date;}
    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}
    public LinkedList <Attendee> getAttendees() {return attendees;}
    public PriorityQueue<WaitingEntry> getWaitingList(){return waitingList;}

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
