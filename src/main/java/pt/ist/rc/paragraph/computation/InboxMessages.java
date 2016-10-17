package pt.ist.rc.paragraph.computation;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Pedro Joaquim.
 */
public class InboxMessages<MV> {

    private ConcurrentHashMap<Integer, List<MV>> messages;

    public InboxMessages() {
        this.messages = new ConcurrentHashMap<>();

    }

    public void addMessageTo(Integer targetVertxID, MV msg){
        if(!messages.containsKey(targetVertxID)){
            messages.put(targetVertxID, new ArrayList<MV>());
        }

        messages.get(targetVertxID).add(msg);
    }

    public void clearInbox(){
       for(Integer key : messages.keySet()){
           messages.get(key).clear();
       }
    }

    public List<MV> getMessages(Integer targetVertxID){
        if(!messages.containsKey(targetVertxID)){
            messages.put(targetVertxID, new ArrayList<MV>());
        }

        return messages.get(targetVertxID);
    }

}
