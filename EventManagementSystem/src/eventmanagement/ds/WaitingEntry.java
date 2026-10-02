package eventmanagement.ds;

import eventmanagement.model.Attendee;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

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

    public static class WaitlistManager {
        private final PriorityQueue<WaitingEntry> waitingList = new PriorityQueue<>();
        private long nextOrder;

        public WaitingEntry addAttendee(Attendee attendee, int priority){
            Objects.requireNonNull(attendee, "Attendee is required");
            WaitingEntry entry = new WaitingEntry(attendee, priority, nextOrder++);
            waitingList.offer(entry);
            return entry;
        }

        public Attendee admitNext(){
            WaitingEntry entry = waitingList.poll();
            return entry == null ? null : entry.getAttendee();
        }

        public WaitingEntry peekNext(){return waitingList.peek();}

        public boolean removeEntry(WaitingEntry entry){return waitingList.remove(entry);}

        public List<WaitingEntry> getWaitingList(){
            List<WaitingEntry> entries = new ArrayList<>(waitingList);
            entries.sort(null);
            return entries;
        }

        public int size(){return waitingList.size();}

        public boolean isEmpty(){return waitingList.isEmpty();}
    }
}
