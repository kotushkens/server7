package com.company;

import Classes.User;
import com.company.db.DatabaseReader;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Шаблон, по которому описывается команда
 */

public abstract class Command implements Serializable {
    private static final long serialVersionUID = 32L;

    public abstract void execute(Object argObject, Socket socket, User user) throws IOException;

    public void execute(User user) {
        if (isNeedLogin()) {

        }
    }

    public boolean isNeedLogin() {
        return true;
    }
}