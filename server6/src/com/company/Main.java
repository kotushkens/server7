package com.company;

import Server.Connections;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(json::SaveCollectionToJson));
        Connections connections = new Connections();
        connections.run(args[0]);
    }

}