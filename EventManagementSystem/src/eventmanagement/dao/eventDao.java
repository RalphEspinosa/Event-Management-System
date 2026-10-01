/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.dao;

import eventmanagement.db.DBConnection;
import eventmanagement.model.Attendee;
import eventmanagement.model.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class eventDao {
    
    private attendeeDao dao = new attendeeDao();
    //Saves a new event 
    public void addEvent(Event event){
       String sql = "INSERT INTO events (name, event_date) VALUES(?, ?)" ;
       
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);){
           
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getDate());
            stmt.executeUpdate();         
       }
       catch (SQLException e){
           System.out.println("Error adding event: " + e.getMessage());
       }
    }
    //Reads every added events 
    public List<Event> getAllEVents(){
        List<Event> events = new ArrayList<>();
        String sql = "SELECT event_id, name, event_date FROM events";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){
            
            while(rs.next()){
                Event event = new Event(
                  rs.getInt("event_id"),
                  rs.getString("name"),
                  rs.getString("event_date")
                );
                List<Attendee> eventAttendees = dao.getAttendeesByEvent(event.getEventId());
                for(Attendee a: eventAttendees){
                    event.addAttendee(a);
                }
                events.add(event);
            }
        }
        catch (SQLException e){
            System.out.println("Error loading events: " + e.getMessage());
        }
        return events;
   }
    
    public Event getEventById(int eventId){
        String sql = "SELECT event_id, name, event_date, FROM events where event_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, eventId);
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Event event = new Event(
                    rs.getInt("event_id"),
                    rs.getString("name"),
                    rs.getString("event_date"));
                    
                    List <Attendee> eventAttendees = dao.getAttendeesByEvent(eventId);
                    for(Attendee a: eventAttendees){
                        event.addAttendee(a);
                    }
                    return event;
                }
            }
       } catch(SQLException e){
                System.out.println("Error loading event: " + e.getMessage());
    }
     return null;
  }
}
