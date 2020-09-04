package Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import Commands.Answer;
import com.company.CollectionManager;

import com.company.json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Connections {

    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static ObjectInputStream in; // поток чтения из сокета
    private static final Logger logger = LoggerFactory.getLogger(Connections.class);

    public void run(String strPort) throws IOException {
        try {
            try {
                int port = 0;
                CollectionManager collectionManager = CollectionManager.getCollectionManager();
                collectionManager.initializeVector();
                logger.info("Создана пустая коллекция");
                json.JsonToCollection();
                try {
                    port = Integer.parseInt(strPort);
                } catch (NumberFormatException ex) {
                    logger.info("Ошибка! Неправильный формат порта");
                    System.exit(0);
                }

                server = new ServerSocket(port);
                logger.info("Сервер запущен!");
                while (true) {
                    clientSocket = server.accept();
                    logger.info("I've been waiting for you to come': " + clientSocket);
                    try {
                        while (true) {
                            in = new ObjectInputStream(clientSocket.getInputStream());
                            Answer answer = new Answer(clientSocket);
                            Object o = in.readObject();
                            answer.toAnswer(o);
                        }

                    } catch (EOFException | SocketException ex) {
                        logger.info(clientSocket + " был и сплыл");
                    } finally {
                        clientSocket.close();
                        if (in != null) { in.close(); }
                    }
                }
            } finally {
                if (clientSocket != null) { clientSocket.close(); }
                logger.info("Сервер закрыт!");
                server.close();
            }
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
            logger.error(String.valueOf(e));
        }
    }

}
