package eventmanagement.model;

import eventmanagement.dao.eventDao;

public class Main {
    public static void main(String[] args) {
        Event sample = new Event(1, "U-Week", "2026-12-08");

        Attendee a1 = new Attendee(1, "Reirii", "reiriiyuu@gmail.com",sample.getEventId());
        Attendee a2 = new Attendee(2, "Ralph", "ralph@gmail.com",sample.getEventId());
        Attendee a3 = new Attendee(3, "Margarette", "margarette@gmail.com",sample.getEventId());

        sample.addAttendee(a1);
        sample.addAttendee(a2);
        sample.addAttendee(a3);

        System.out.println(sample);
        System.out.println("Attendee list: ");
        for (Attendee a : sample.getAttendees()) {
            System.out.println(" - " + a);
        }

        eventDao Eventdao = new eventDao();
        for (Event e : Eventdao.getAllEVents()) {
            System.out.println(e);
            for (Attendee a : e.getAttendees()) {
                System.out.println("  - " + a);
            }
        }
    }
}
