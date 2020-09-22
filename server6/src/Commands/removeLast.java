package Commands;

import Classes.User;
import com.company.Command;
import com.company.CommandReciever;

import java.io.IOException;
import java.net.Socket;

public class removeLast extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket, User user) throws IOException {
        CommandReciever commandReciever = new CommandReciever(socket);
        commandReciever.removeLast(user);
    }
}
