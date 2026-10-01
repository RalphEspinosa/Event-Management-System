/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eventmanagement.model;

//testing lang buseng

import eventmanagement.dao.attendeeDao;
import eventmanagement.dao.eventDao;
import eventmanagement.db.DBConnection;
import java.sql.Connection;
public class Main {
    public static void main(String[] args) {
       
        Event sample = new Event(1, "U-Week", "2026-12-08"); 
        
        Attendee a1 = new Attendee(1, "Reirii", "reiriiyuu@gmail.com",sample.getEventId());
        Attendee a2 = new Attendee(2, "Ralph", "ralph@gmail.com",sample.getEventId());
        
        sample.addAttendee(a1);
        sample.addAttendee(a2);
        
        System.out.println(sample);
        
        System.out.println("Attendee list: ");
        for(Attendee a: sample.getAttendees()){
            System.out.println(" - " + a);
        }
        
        try(Connection conn = DBConnection.getConnection()){
            System.out.println("Database connected succesfully");
        }catch(Exception e){
                 System.out.println("Connection failed");
                 }
        
        attendeeDao dao = new attendeeDao();
        dao.addAttendee(new Attendee(0, "reirii", "reiriiyuu@gmail.com", 2));
        
        for(Attendee a: dao.getAttendeesByEvent(2)){
            System.out.println(a);
        }
    }  
}
