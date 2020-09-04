package Commands;

import Classes.SpaceMarine;
import com.company.Command;
import com.company.CommandReciever;

import java.io.IOException;
import java.net.Socket;

public class Update extends Command {
    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket) throws IOException {
        SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) argObject;
        String arg = combinedCommand.getArg();
        SpaceMarine spaceMarine = (SpaceMarine) combinedCommand.getObject();
        if (arg.split(" ").length == 1) {
            CommandReciever commandReciever = new CommandReciever(socket);
            commandReciever.update(arg, spaceMarine);
        }
    }
}
