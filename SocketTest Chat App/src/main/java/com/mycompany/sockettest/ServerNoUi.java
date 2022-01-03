package com.mycompany.sockettest;

import java.io.*;
import java.net.*;

/**
 *
 * @author nihalshahria
 */
public class ServerNoUi {

    public final int port = 6666;
    private ServerSocket server;
    private Socket socket;

    public static void main(String[] args) throws IOException{
        new ServerNoUi().start();
    }

    public void start() throws IOException{
        server = new ServerSocket(port);
        socket = server.accept();
        new ChatHandlar(socket, "Nihal").start();        
    }
}
