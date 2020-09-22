package com.company;

import Classes.User;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class Info extends Command implements Serializable {

    private static final long serialVersionUID = 32L;

    @Override
    public void execute(Object argObject, Socket socket, User user) throws IOException {
        CommandReciever commandReciever = new CommandReciever(socket);
        commandReciever.info(user);
    }
}
