/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.dao;

import eventmanagement.db.DBConnection;
import eventmanagement.model.Attendee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendeeDao {
    public void addAttendee(Attendee attendee){
        String sql = "INSERT INTO attendees (name, email, event_id, checked_in) VALUES(?, ?, ?, ?)";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, attendee.getName());
            stmt.setString(2, attendee.getEmail());
            stmt.setInt(3, attendee.getEventId());
            stmt.setBoolean(4, attendee.isCheckedIn());
            stmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error adding attendee: " + e.getMessage());
        }
    }
    public boolean checkInAttendee(int attendeeId){
        return updateCheckInStatus(attendeeId, true);
    }

    public boolean updateCheckInStatus(int attendeeId, boolean checkedIn){
        String sql = "UPDATE attendees SET checked_in = ? WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setBoolean(1, checkedIn);
            stmt.setInt(2, attendeeId);
            return stmt.executeUpdate() > 0;
        }
        catch(SQLException e){
            System.out.println("Error updating attendee check-in status: " + e.getMessage());
            return false;
        }
    }

    public void removeAttendee(int attendeeId){
        String sql = "DELETE FROM attendees WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, attendeeId);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            System.out.println("Error removing attendee: " + e.getMessage());
        }
    }
    public List <Attendee> getAttendeesByEvent(int eventId){
        List<Attendee> attendees = new ArrayList<>();
        String sql = "SELECT id, name, email, event_id, checked_in FROM attendees WHERE event_id = ?";
        
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
            
            stmt.setInt(1, eventId);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    Attendee a= new Attendee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getInt("event_id"),
                    rs.getBoolean("checked_in")
                  );
                    attendees.add(a);
                }
            } 
        } catch(SQLException e){
            System.out.println("Error loeading attendees: " + e.getMessage());
        }
        return attendees;
    }
}
