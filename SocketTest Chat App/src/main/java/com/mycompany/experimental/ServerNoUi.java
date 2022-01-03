/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.experimental;

import java.io.*;
import java.net.*;

/**
 *
 * @author nihalshahria
 */
public class ServerNoUi {

    ServerSocket server;
    Socket socket;
    final int port = 6666;
    DataInputStream in;
    BufferedReader inConsole;
    DataOutputStream out;

    public void start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();
        String userName = System.getProperty("user.name");
        String folderPath = "/home/" + userName + "/SocketApp/Server/"; //folderPath to save incoming files
        mkDir(folderPath);  //create new folder according to folder path
        
        // streams initializations
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        inConsole = new BufferedReader(new InputStreamReader(System.in));
        /////////////////////////
        
        // Message send
        new Thread(() -> {
            while (true) {
                try {
                    String msgOut = inConsole.readLine();   //read input from terminal
                    String[] args = msgOut.split(" ", 2);
                    // if(msgOut starts with "-f ", that means next part of the msg will contain path of the file)
                    
                    if ("-f".equals(args[0]) && args.length == 2) { 
                        String path = args[1];
                        System.out.println(path);
                        File file = new File(path);
                        if (file.exists()) {
                            System.out.println("Sent " + file.getPath());
                            out.writeUTF("-f " + file.getName());
                            
                            // send file
                            int count;
                            byte[] buffer = new byte[65536];
                            InputStream fileIn;
                            try {
                                fileIn = new FileInputStream(file);
                                OutputStream fileOut = socket.getOutputStream();
                                while ((count = fileIn.read(buffer)) > 0) {
                                    fileOut.write(buffer, 0, count);
                                }
                                System.out.println("file sent");
                                fileOut.flush();
                                
//                                    fileOut.close();
//                                    fileIn.close();
//                                    restStreams();
                                try {
                                    out = new DataOutputStream(socket.getOutputStream());
                                } catch (IOException e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                            }
                            //////////////////////////
                            
                        } else {
                            System.out.println("File doesn't exist");
                        }
                    } else {
                        
                        // send message
                        System.out.println("From Server: " + msgOut);
                        out.writeUTF(msgOut);
                        out.flush();
                        ///////////////////
                        
                    }

                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }).start();

        // Message receive
        new Thread(() -> {
            while (true) {
                try {
                    String msgIn = in.readUTF();
                    System.out.println("From Client: " + msgIn);
                    
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }).start();
        /////////////////////

    }

    private static boolean mkDir(String name) {
        File dir = new File(name);
        if (dir.exists()) {
            return false;
        }
        if (dir.mkdirs()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
        new ServerNoUi().start();
    }
}
