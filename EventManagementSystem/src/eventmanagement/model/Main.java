/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package eventmanagement.model;

/**
 *
 * @author reirii
 */
//testing lang buseng
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
    }
    
}
