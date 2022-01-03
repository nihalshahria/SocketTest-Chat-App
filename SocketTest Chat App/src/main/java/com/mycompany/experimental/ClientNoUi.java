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
                            
                            // send file
                            FileInputStream fileIn = new FileInputStream(file);
                            byte[] buffer = new byte[(int)file.length()];
                            fileIn.read(buffer);
                            out.writeInt(buffer.length);
                            out.write(buffer);
                            out.flush();
                            //////////////////////////
                            
                        } else {
                            System.out.println("File doesn't exist");
                        }
                    } else {
                        
                        // send message
                        System.out.println("From Client: " + msgOut);
                        out.writeUTF(msgOut);
                        out.flush();
                        ///////////////////
                        
                    }

                } catch (IOException ex) {
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
                        File file = new File(folderPath + args[1]);
                        FileOutputStream fileOut;
                        int fileLength = in.readInt();
                        if(fileLength > 0){
                            byte[] buffer = new byte[fileLength];
                            in.readFully(buffer, 0, fileLength);
                            fileOut = new FileOutputStream(file);
                            fileOut.write(buffer, 0, fileLength);
                            fileOut.close();
                            System.out.println("File received");
                            System.out.println("Saved in " + file.getAbsolutePath());
                            msgIn = args[1];
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
