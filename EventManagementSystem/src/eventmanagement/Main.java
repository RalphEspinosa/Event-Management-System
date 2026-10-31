package eventmanagement;

import eventmanagement.dao.AttendeeDao;
import eventmanagement.dao.WaitlistDao;
import eventmanagement.dao.EventDao;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Attendee;
import eventmanagement.model.Attendee;
import eventmanagement.model.Event;
import eventmanagement.model.Event;
import eventmanagement.ds.WaitlistManager;

//testing lang buseng
public class Main {
    public static void main(String[] args) {
        Event sample = new Event(1, "U-Week", "2026-12-08");

        System.out.println(sample);
        System.out.println("Attendee list: ");
        for (Attendee a : sample.getAttendees()) {
            System.out.println(" - " + a);
        }

        EventDao Eventdao = new EventDao();
        for (Event e : Eventdao.getAllEVents()) {
            System.out.println(e);
            for (Attendee a : e.getAttendees()) {
                System.out.println("  - " + a);
            }
        }
        
        
        WaitlistManager manager = new WaitlistManager();
        
        Attendee a1 = new Attendee(0, "Reirii", "reiriiyuu@gmail.com",3);
        Attendee a2 = new Attendee(0, "Ralph", "ralph@gmail.com",3);
        Attendee a3 = new Attendee(0, "Margarette", "margarette@gmail.com",3);
        
        manager.addToWaitlist(3, new WaitingEntry(a1, 3, System.currentTimeMillis()));
        manager.addToWaitlist(3, new WaitingEntry(a2, 2, System.currentTimeMillis()));
        manager.addToWaitlist(3, new WaitingEntry(a3, 1, System.currentTimeMillis()));
        
        System.out.println("Next: " + manager.pollNext(3));
        
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