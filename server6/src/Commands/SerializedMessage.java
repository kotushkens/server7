package Commands;

import java.io.Serializable;

public class SerializedMessage implements Serializable {

    private String message;

    public SerializedMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
