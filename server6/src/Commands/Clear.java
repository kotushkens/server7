package Commands;

import Classes.User;
import com.company.Command;
import com.company.CommandReciever;

import java.io.IOException;
import java.net.Socket;

public class Clear extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket, User user) throws IOException {
        CommandReciever commandReceiver = new CommandReciever(socket);
        commandReceiver.clear(user);
    }
}
