package pt.ist.rc.paragraph.computation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Joaquim.
 */
public class InboxMessages<MV> {

    private List<Message<MV>> messages;

    public InboxMessages() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message<MV> msg){
        messages.add(msg);
    }

    public void clearInbox(){
        this.messages = new ArrayList<>();
    }

    public List<Message<MV>> getMessages(){
        return this.messages;
    }
}
