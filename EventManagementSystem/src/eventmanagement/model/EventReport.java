
package eventmanagement.model;

public class EventReport {
    private final int eventId;
    private final String name;
    private final String date;
    private final String category;
    private final int capacity;
    private final int registered;
    private final int checkedIn;
    private final int waitlisted;
    private final int feedbackCount;
    private final double averageRating;
    
    public EventReport(int eventId, String name, String date, String category, int capacity, int registered,
                       int checkedIn, int waitlisted, int feedbackCount, double averageRating){
        this.eventId = eventId;
        this.name = name;
        this.date = date;
        this.category = category;
        this.capacity = capacity;
        this.registered = registered;
        this.checkedIn = checkedIn;
        this.waitlisted = waitlisted;
        this.feedbackCount = feedbackCount;
        this.averageRating = averageRating;
    }
    
    public int getEventId(){return eventId;}
    public String getName(){return name;}
    public String getDate(){return date;}
    public String getCategory(){return category;}
    public int getCapacity(){return capacity;}
    public int getRegistered(){return registered;}
    public int getCheckedIn(){return checkedIn;}
    public int getWaitlisted(){return waitlisted;}
    public int getFeedbackCount(){return feedbackCount;}
    public double getAverageRating(){return averageRating;}
    
    public double getAttendanceRate(){
        if(registered == 0){
            return 0;
        }
       return checkedIn * 100 / registered;
    }
}
