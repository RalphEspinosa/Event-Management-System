package eventmanagement.ds;

import eventmanagement.model.Attendee;

public class WaitingEntry implements Comparable<WaitingEntry> {
    private final Attendee attendee;
    private final int priority;
    private final long order;

    public WaitingEntry(Attendee attendee, int priority, long order){
        this.attendee = attendee;
        this.priority = priority;
        this.order = order;
    }

    public Attendee getAttendee(){return attendee;}

    @Override
    public int compareTo(WaitingEntry other){
        int result = Integer.compare(priority, other.priority);
        return result == 0 ? Long.compare(order, other.order) : result;
    }

    @Override
    public String toString(){
        return "Priority " + priority + " - " + attendee;
    }
}
