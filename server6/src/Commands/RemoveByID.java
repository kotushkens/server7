package Commands;

import Classes.User;
import com.company.Command;
import com.company.CommandReciever;

import java.io.IOException;
import java.net.Socket;

public class RemoveByID extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket, User user) throws IOException {
        String arg = argObject.toString();
        if (arg.split(" ").length == 1) {
            CommandReciever commandReceiver = new CommandReciever(socket);
            commandReceiver.remove_by_id(arg, user);
        } else {
            System.out.println("Проверьте количество аргументов");
        }
    }
}

