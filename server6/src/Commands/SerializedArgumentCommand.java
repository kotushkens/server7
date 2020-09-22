package Commands;

import com.company.Command;
import Classes.User;

import java.io.Serializable;

public class SerializedArgumentCommand implements Serializable {

    private Command command;
    private String arg;
    private User user;

    public SerializedArgumentCommand(Command command, String arg, User user) {
        this.command = command;
        this.arg = arg;
        this.user = user;
    }

    public Command getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public User getUser() {
        return user;
    }
}
