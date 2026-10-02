/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagement.ds;

import java.util.LinkedList;
import java.util.ArrayList;
import eventmanagement.model.Event;

/**
 *
 * @author reirii
 */
public class dataStructures {
    private static String searchValue(Event event, boolean byDate){
        return byDate ? event.getDate() : event.getCategory();
    }

    public static LinkedList<Event> linearSearch(LinkedList<Event> events, String search, boolean byDate){
        LinkedList<Event> results = new LinkedList<>();
        for(Event event : events){
            if(searchValue(event, byDate).equalsIgnoreCase(search)){
                results.add(event);
            }
        }
        return results;
    }

    public static LinkedList<Event> binarySearch(LinkedList<Event> events, String search, boolean byDate){
        LinkedList<Event> results = new LinkedList<>();
        ArrayList<Event> sortedEvents = new ArrayList<>(events);
        sortedEvents.sort((first, second) -> searchValue(first, byDate)
            .compareToIgnoreCase(searchValue(second, byDate)));
        int low = 0;
        int high = sortedEvents.size() - 1;
        while(low <= high){
            int middle = low + (high - low) / 2;
            int comparison = searchValue(sortedEvents.get(middle), byDate)
                .compareToIgnoreCase(search);
            if(comparison < 0){
                low = middle + 1;
            }else{
                high = middle - 1;
            }
        }
        while(low < sortedEvents.size()
                && searchValue(sortedEvents.get(low), byDate).equalsIgnoreCase(search)){
            results.add(sortedEvents.get(low));
            low++;
        }
        return results;
    }
}
