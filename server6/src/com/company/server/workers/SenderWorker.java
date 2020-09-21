package com.company.server.workers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SenderWorker implements Runnable {

    private final ObjectOutputStream oos;
    private final Object content;
    private final Logger logger = LoggerFactory.getLogger(SenderWorker.class);

    public SenderWorker(final Socket socket,
                        final Object content) throws IOException{
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.content = content;
    }

    @Override
    public void run() {
        try {
            oos.writeObject(content);
        } catch (IOException e) {
            logger.error("Не получилось отправить сообщение.", e);
        }
    }

}
