package Commands;

import Classes.User;
import com.company.Command;
import com.company.db.DatabaseReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class Answer {

    private final Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(Answer.class);

    public Answer(Socket socket) {
        this.socket = socket;
    }

    public void toAnswer(Object o) throws IOException {
        if (o instanceof SerializedSimplyCommand) {
            SerializedSimplyCommand simplyCommand = (SerializedSimplyCommand) o;
            Command command = simplyCommand.getCommand();
            String arg = "";
            command.execute(arg, socket, getUserFromDb(command, simplyCommand.getUser()));
        }

        if (o instanceof SerializedArgumentCommand) {
            SerializedArgumentCommand argumentCommand = (SerializedArgumentCommand) o;
            Command command = argumentCommand.getCommand();
            String arg = argumentCommand.getArg();
            command.execute(arg, socket, getUserFromDb(command, argumentCommand.getUser()));
        }

        if (o instanceof SerializedObjectCommand) {
            SerializedObjectCommand objectCommand = (SerializedObjectCommand) o;
            Command command = objectCommand.getCommand();
            Object arg = objectCommand.getObject();
            command.execute(arg, socket, getUserFromDb(command, objectCommand.getUser()));
        }

        if (o instanceof SerializedCombinedCommand) {
            SerializedCombinedCommand combinedCommand = (SerializedCombinedCommand) o;
            Command command = combinedCommand.getCommand();
            command.execute(combinedCommand, socket, getUserFromDb(command, combinedCommand.getUser()));
        }
    }

    private User getUserFromDb(Command command, User user) {
        if (command.isNeedLogin()) {
            try (DatabaseReader con = new DatabaseReader()) {
                return con.getUserByLogin(user.getLogin()).orElse(null);
            } catch (SQLException e) {
                logger.error("Не получилось получить юзера по логину.", e);
                return null;
            }
        } else {
            return null;
        }
    }

}
