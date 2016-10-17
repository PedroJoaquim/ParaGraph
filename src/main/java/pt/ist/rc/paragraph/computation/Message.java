package pt.ist.rc.paragraph.computation;

public abstract class Message<MV> {

    private MV messageValue;

    public Message(MV messageValue) {
        this.messageValue = messageValue;
    }

    public MV getMessageValue() {
        return messageValue;
    }
}
