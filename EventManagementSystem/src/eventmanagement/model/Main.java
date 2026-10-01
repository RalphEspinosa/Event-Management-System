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
        eventDao Eventdao = new eventDao();
        
        for(Event e: Eventdao.getAllEVents()){
            System.out.println(e);
            for(Attendee a: e.getAttendees()){
                System.out.println("  - " + a);
            }
        }
    }  
}
