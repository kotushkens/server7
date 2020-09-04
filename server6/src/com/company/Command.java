package com.company;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

/**
 * Шаблон, по которому описывается команда
 */

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 32L;
    public abstract void execute(Object argObject, Socket socket) throws IOException;
}