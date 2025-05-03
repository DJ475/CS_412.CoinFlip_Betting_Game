import java.io.Serializable;

public class MessageClass implements Serializable {
    private String action;
    private String errorMessage;

    public MessageClass() {
    }

    public MessageClass(String action, String errorMessage) {
        this.action = action;
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return String.format("%s::::%s",action,errorMessage);
    }
}
