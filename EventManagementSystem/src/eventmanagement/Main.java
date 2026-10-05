package eventmanagement;

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
        
    }
}
