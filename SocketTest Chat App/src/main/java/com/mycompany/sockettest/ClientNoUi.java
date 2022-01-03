package com.mycompany.sockettest;

import java.io.*;
import java.net.*;

/**
 *
 * @author nihalshahria
 */
public class ClientNoUi {

    public final int port = 6666;
    private Socket socket;

    public static void main(String[] args) throws IOException {
        new ClientNoUi().start();
    }

    public void start() throws IOException {
        socket = new Socket("localhost", port);
        new ChatHandlar(socket, "Shahria").start();
        
    }
}
