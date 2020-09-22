package Commands;

import com.company.Command;
import com.company.CommandReciever;
import Classes.User;

import java.io.IOException;
import java.net.Socket;

public class Register extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket, User user) throws IOException {
        CommandReciever commandReciever = new CommandReciever(socket);
        commandReciever.register((User) argObject);
    }

    @Override
    public boolean isNeedLogin() {
        return false;
    }
}
