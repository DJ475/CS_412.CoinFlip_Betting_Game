import java.io.Serializable;

public class MessageClass implements Serializable {
    private String action;
    private String stringMessage;

    public MessageClass() {
    }

    public MessageClass(String action, String message) {
        this.action = action;
        this.stringMessage = message;
    }


    @Override
    public String toString() {
        return String.format("%s::::%s",action,stringMessage);
    }
}
