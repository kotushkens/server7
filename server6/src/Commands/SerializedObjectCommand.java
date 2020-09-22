package Commands;

import com.company.Command;
import Classes.User;

import java.io.Serializable;

public class SerializedObjectCommand implements Serializable {

    private Command command;
    private Object object;
    private User user;

    public SerializedObjectCommand(Command command, Object object, User user) {
        this.command = command;
        this.object = object;
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public User getUser() {
        return user;
    }
}
