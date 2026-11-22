package eventmanagement.dao;

import eventmanagement.db.DBConnection;
import eventmanagement.model.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDao {

    public boolean addFeedback(Feedback feedback){
        String sql = "INSERT INTO feedback (event_id, attendee_id, rating, comments) VALUES (?, ?, ?, ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, feedback.getEventId());
            stmt.setInt(2, feedback.getAttendeeId());
            stmt.setInt(3, feedback.getRating());
            stmt.setString(4, feedback.getComments());
            stmt.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("Error adding feedback: " + e.getMessage());
            return false;
        }
    }

    public boolean hasFeedback(int eventId, int attendeeId){
        String sql = "SELECT 1 FROM feedback WHERE event_id = ? AND attendee_id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, eventId);
            stmt.setInt(2, attendeeId);
            try(ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        }catch(SQLException e){
            System.out.println("Error checking feedback: " + e.getMessage());
            return false;
        }
    }

    public List<Feedback> getFeedbackByEvent(int eventId){
        List<Feedback> feedbackList = new ArrayList<>();
        String sql = "SELECT f.feedback_id, f.event_id, f.attendee_id, a.name, f.rating, f.comments "
                   + "FROM feedback f JOIN attendees a ON a.id = f.attendee_id "
                   + "WHERE f.event_id = ? ORDER BY f.feedback_id";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, eventId);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    feedbackList.add(new Feedback(
                        rs.getInt("feedback_id"),
                        rs.getInt("event_id"),
                        rs.getInt("attendee_id"),
                        rs.getString("name"),
                        rs.getInt("rating"),
                        rs.getString("comments")
                    ));
                }
            }
        }catch(SQLException e){
            System.out.println("Error loading feedback: " + e.getMessage());
        }
        return feedbackList;
    }
}