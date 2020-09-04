package Commands;

import Classes.SpaceMarine;
import com.company.Command;
import com.company.CommandReciever;

import java.io.IOException;
import java.net.Socket;
import java.util.NoSuchElementException;

public class removeLast extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        CommandReciever commandReciever = new CommandReciever(socket);
        commandReciever.removeLast();
    }
}
