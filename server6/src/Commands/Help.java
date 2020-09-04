package Commands;

import com.company.Command;
import com.company.CommandReciever;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Help extends Command implements Serializable {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        CommandReciever commandReceiver = new CommandReciever(socket);
        commandReceiver.info();
    }
}
