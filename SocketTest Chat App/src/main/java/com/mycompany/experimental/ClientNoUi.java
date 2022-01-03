package com.mycompany.experimental;

import java.io.*;
import java.net.*;

/**
 *
 * @author nihalshahria
 */
public class ClientNoUi {

    Socket socket;
    DataInputStream in;
    BufferedReader inConsole;
    DataOutputStream out;
    final int port = 6666;
    String folderPath;

    public void start() throws IOException {
        socket = new Socket("localhost", port);
        String userName = System.getProperty("user.name");
        folderPath = "/home/" + userName + "/SocketApp/Client/"; //folderPath to save incoming files
        mkDir(folderPath);   //create new folder according to folder path

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

                        } else {
                            System.out.println("File doesn't exist");
                        }
                    } else {
                        System.out.println("From Client: " + msgOut);
                        out.writeUTF(msgOut);
                    }

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }).start();

        // receive
        new Thread(() -> {
            while (true) {
                try {

                    String msgIn = in.readUTF();
                    String[] args = msgIn.split(" ", 2);
                    // if(msgIn starts with "-f ", that means next part of the msg will contain name of the file)
                    if ("-f".equals(args[0]) && args.length == 2) {

                        //file receive
                        InputStream fileIn = null;
                        OutputStream fileOut = null;
                        File file = new File(folderPath + args[1]);
                        try {
                            fileIn = socket.getInputStream();
                            fileOut = new FileOutputStream(file);
                            int count;
                            byte[] buffer = new byte[65536];
                            while ((count = fileIn.read(buffer)) > 0) {
                                fileOut.write(buffer, 0, count);
                            }
                            fileOut.flush();
//                            fileIn.close();
//                            fileOut.close();
//                            System.out.println(file.getName() + " received");
//                            System.out.println("file saved in " + file.getAbsolutePath());
                            try {
                                in = new DataInputStream(socket.getInputStream());

                            } catch (Exception e) {
                                System.out.println("in " + e.getMessage());
                            }
//                            restStreams();            F
                            continue;
                            //////////////////////

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    System.out.println("From Server: " + msgIn);

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }).start();

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
        new ClientNoUi().start();
    }

}
