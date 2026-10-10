package eventmanagement;

import eventmanagement.dao.AttendeeDao;
import eventmanagement.dao.EventDao;
import eventmanagement.ds.Search;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Attendee;
import eventmanagement.model.Event;
import eventmanagement.ds.WaitlistManager;
import java.util.*;
//testing lang buseng
public class Main {
    public static void main(String[] args) {
        
        //
        EventDao eventDao = new EventDao();
        AttendeeDao attendeeDao = new AttendeeDao();
        
        //eventDao.addEvent(new Event(0, "U_WEEK", "2026-11-20", 50, "Academic"));
        //eventDao.addEvent(new Event(0, "CosMania", "2026-8-4", 1, "Cosplay convention"));
        
        for (Event e : eventDao.getAllEvents()) {
            System.out.println(e + " | full? " + e.isFull());
        }
        
        int found= -1;
        for(Event e: eventDao.getAllEvents()){
            if(e.getName().equals("CosMania")){
                found = e.getEventId();
            }
        }
       // attendeeDao.addAttendee(new Attendee(0, "reiriin", "yuurei@gmail.com", found));
        Event id = eventDao.getEventById(found);
        System.out.println(id + " | full? " + id.isFull());
        
         LinkedList<Event> all = new LinkedList<>(eventDao.getAllEvents());
         
         for(Event e: Search.linearSearch(all, "Academic", false)){
             System.out.println("  " + e);
         }
         for(Event e: Search.binarySearch(all, "Cosplay convention", false)){
             System.out.println(" " + e);
         }
         
         
    }
}
