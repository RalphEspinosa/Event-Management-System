package eventmanagement;

import eventmanagement.dao.AttendeeDao;
import eventmanagement.dao.WaitlistDao;
import eventmanagement.dao.EventDao;
import eventmanagement.ds.Search;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Attendee;
import eventmanagement.model.Event;
import java.util.*;
//testing lang buseng
public class Main {
    public static void main(String[] args) {
        WaitlistDao dao = new WaitlistDao();
        int eventId = 5;
        
        //Add to waitlist
        System.out.println("Add to waitlist " + dao.addToWaitlist(new Attendee(0, "Ralph", "ralph@gmail.com", eventId), 3));
        System.out.println("Add to waitlist " + dao.addToWaitlist(new Attendee(0, "Reirii", "reirii@gmail.com", eventId), 3));
        System.out.println("Add to waitlist " + dao.addToWaitlist(new Attendee(0, "Simeon", "simeon@gmail.com", eventId), 1));
        
        //Check for duplicates
        System.out.println("Add Ralph again " + dao.addToWaitlist(new Attendee(0, "Ralph", "ralph@gmail.com", eventId), 3));
        
        //Check if someone is on the list
        System.out.println("Is ralph on the list? " + dao.isOnWaitlist(eventId, "ralph@gmail.com"));
        System.out.println("Is marga on the list? " + dao.isOnWaitlist(eventId, "marga@gmail.com"));
        
        //Load into a priority queue
        WaitingEntry.WaitlistManager manager = new WaitingEntry.WaitlistManager();
        dao.loadInto(eventId, manager);
        System.out.println("Waiting list: " + manager.getWaitingList());
        
        //Remove attendee, then reload 
        System.out.println("Remove Simeon: " + dao.removeFromWaitlist(eventId, "simeon@gmail.com"));
        System.out.println("Remove Simeon again: " + dao.removeFromWaitlist(eventId, "simeon@gmail.com"));
        
        WaitingEntry.WaitlistManager after = new WaitingEntry.WaitlistManager();
        dao.loadInto(eventId, after);
        System.out.println("After removal: " + after.getWaitingList());
    }
}