/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.dao;

import eventmanagement.db.DBConnection;
import eventmanagement.ds.WaitingEntry;
import eventmanagement.model.Attendee;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WaitlistDao {
    //Add attendee to the event's current waitlist
    public boolean addToWaitlist(Attendee attendee, int priority){
        String sql = "INSERT INTO waitlist (event_id, name, email, priority) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, attendee.getEventId());
            stmt.setString(2, attendee.getName());
            stmt.setString(3, attendee.getEmail());
            stmt.setInt(4, priority);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding to waitlist: " + e.getMessage());
            return false;
        }
   } //Check if the attendee is on the current  event waitlist
    public boolean isOnWaitlist(int eventID, String email){
        String sql = "SELECT 1 FROM waitlist WHERE event_id = ? and email = ?" ; 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventID);
            stmt.setString(2, email);
            try (ResultSet rs= stmt.executeQuery()){
                return rs.next();
            }
        }
        catch (SQLException e){
            System.out.println("Error checking waitlist: " + e.getMessage());
            return false;
        }
    }
     // Fills a priority queue with this event's waiting people, oldest first
    public void loadInto(int eventId, WaitingEntry.WaitlistManager manager){
        String sql = "SELECT name, email, priority FROM waitlist WHERE event_id = ? ORDER BY waitlist_id";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    Attendee a = new Attendee(0, rs.getString("name"), rs.getString("email"), eventId);
                    manager.addAttendee(a, rs.getInt("priority"));
                }
            }
           
           }catch (SQLException e){
                System.out.println("Error loading waitlist: " + e.getMessage());
        }
    }
    public boolean removeFromWaitlist(int eventId, String email){
        String sql = "DELETE FROM waitlist WHERE event_id = ? AND email = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, eventId);
            stmt.setString(2, email);
            return stmt.executeUpdate() > 0;
            
        } catch(SQLException e){
            System.out.println("Error removing from waitlist: " + e.getMessage());
            return false;
        }
    }
}
