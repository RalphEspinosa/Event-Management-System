/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.model;

/**
 *
 * @author reirii
 */
public class Attendee {
    private int id;
    private String name;
    private String email;
    private int eventId;
    
    // Attendees Constructor （︶^︶）
    public Attendee(int id, String name, String email, int eventId){
        this.id = id;
        this.name = name;
        this.email = email;
        this.eventId = eventId;
    }
    //Setters and getters ヾ(≧へ≦)〃
    public int getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public int getEventId(){return eventId;}
    
    public void setName(String name){this.name = name;}
    public void setEmail(String email){this.email = email;}
    
    @Override
    public String toString(){
        return name + " (" + email + ")";
    }
}
