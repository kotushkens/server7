package Commands;

import com.company.Command;
import Classes.User;

import java.io.Serializable;

public class SerializedCombinedCommand implements Serializable {

    private Command command;
    private Object object;
    private String arg;
    private User user;

    public SerializedCombinedCommand(Command command, Object object, String arg, User user) {
        this.command = command;
        this.object = object;
        this.arg = arg;
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public Object getObject() {
        return object;
    }

    public String getArg() {
        return arg;
    }

    public User getUser() {
        return user;
    }
}
