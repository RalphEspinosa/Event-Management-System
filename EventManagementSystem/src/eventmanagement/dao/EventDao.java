/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.dao;

import eventmanagement.db.DBConnection;
import eventmanagement.model.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    
    private final AttendeeDao attendeeDao = new AttendeeDao();
    private final WaitlistDao waitlistDao = new WaitlistDao();
    //Saves a new event 
    public boolean addEvent(Event event){
       String sql = "INSERT INTO events (name, event_date, capacity, category) VALUES(?, ?, ?, ?)" ;
       
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
           
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDate());
            stmt.setInt(3, event.getCapacity());
            stmt.setString(4, event.getCategory());
            return stmt.executeUpdate() == 1;
       }
       catch (SQLException e){
           System.out.println("Error adding event: " + e.getMessage());
           return false;
       }
    }
    //Updates an existing event
    public void updateEvent(Event event){
        String sql = "UPDATE events SET name = ?, event_date = ?, capacity = ?, category = ? WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDate());
            stmt.setInt(3, event.getCapacity());
            stmt.setString(4, event.getCategory());
            stmt.setInt(5, event.getEventId());
            stmt.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Error updating event: " + e.getMessage());
        }
    }

    public boolean deleteEvent(int eventId){
        String sql = "DELETE FROM events WHERE event_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                attendeeDao.deleteByEvent(conn, eventId);
                waitlistDao.deleteByEvent(conn, eventId);

                int deleted;
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, eventId);
                    deleted = stmt.executeUpdate();
                }
                if (deleted == 0) {
                    conn.rollback();
                    return false;
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Error deleting event: " + e.getMessage());
            return false;
        }
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id, name, event_date, capacity, category FROM events";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                events.add(readEvent(rs));
            }
        }
        for (Event event : events) {
            event.getAttendees().addAll(attendeeDao.getAttendeesByEvent(event.getEventId()));
        }
        return events;
    }

    public Event getEventById(int eventId) {
        String sql = "SELECT event_id, name, event_date, capacity, category FROM events WHERE event_id = ?";
        Event event;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                event = readEvent(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error loading event: " + e.getMessage());
            return null;
        }
        event.getAttendees().addAll(attendeeDao.getAttendeesByEvent(eventId));
        return event;
    }

    private Event readEvent(ResultSet rs) throws SQLException {
        return new Event(rs.getInt("event_id"), rs.getString("name"),
            rs.getString("event_date"), rs.getString("category"), rs.getInt("capacity"));
    }
}
