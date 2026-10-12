/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.ds;

import java.util.*;

public class WaitlistManager {
    private Map <Integer, PriorityQueue<WaitingEntry>> waitlists = new HashMap<>();
    
    public void addToWaitlist(int eventId,WaitingEntry entry){
        waitlists.putIfAbsent(eventId, new PriorityQueue<>());
        waitlists.get(eventId).add(entry);
    }
    
    public WaitingEntry pollNext(int eventId){
        PriorityQueue<WaitingEntry> queue = waitlists.get(eventId);
        if (queue == null || queue.isEmpty()){
            return null;
        }
        return queue.poll();
    }
    public int waitlistSize(int eventId){
        PriorityQueue<WaitingEntry> queue = waitlists.get(eventId);
        
        if(queue == null){
            return 0;
        }
        else{
            return queue.size();
        }
    }
}