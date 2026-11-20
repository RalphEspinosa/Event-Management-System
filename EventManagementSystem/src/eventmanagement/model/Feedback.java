/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.model;

public class Feedback {
    private final int feedbackId;
    private final int eventId;
    private final int attendeeId;
    private final String attendeeName;
    private final int rating;
    private final String comments;
    
    public Feedback(int feedbackId, int eventId, int attendeeId, String attendeeName, int rating, String comments){
        this.feedbackId = feedbackId;
        this.eventId = eventId;
        this.attendeeId = attendeeId;
        this.attendeeName = attendeeName;
        this.rating = rating;
        this.comments = comments;
    }
    
    public int getFeedbackId(){
        return feedbackId;
    }
    public int getEventId(){
        return eventId;
    }
    public int getAttendeeId(){
        return attendeeId;
    }
    public String getAttendeeName(){
        return attendeeName;
    }
    public int getRating(){
        return rating;
    }
    public String getComments(){
        return comments;
    }
    
    @Override
    public String toString(){
        return attendeeName + ": " + rating + " /5" + (comments.isEmpty() ? "" : " - " + comments);
    }
}
