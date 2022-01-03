package com.mycompany.sockettest;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author nihalshahria
 */
public class ChatHandlar extends Thread{
    public String folderPath;
    public String userName;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private final BufferedReader inConsole;

    public ChatHandlar(Socket socket, String userName) throws IOException{
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        inConsole = new BufferedReader(new InputStreamReader(System.in));
        this.userName = userName;
    }

    private boolean mkDir() {
        String user = System.getProperty("user.name");
        folderPath = "/home/" + user + "/SocketApp/" + userName + "/"; //folderPath to save incoming files
        File dir = new File(folderPath);
        if (dir.exists()) {
            return false;
        }
        return dir.mkdirs();
    }
    
    public void sendMessage() throws IOException {
        String msgOut = inConsole.readLine();   //read input from terminal
        msgOut = userName + "--> " + msgOut;
        String[] args = msgOut.split(" ", 3);
        // if(msgOut starts with "-f ", that means next part of the msg will contain path of the file)

        if ("-f".equals(args[1]) && args.length == 3) {
            sendFile(args);
        } else {
//            msgOut = userName + "--> " + msgOut;
            System.out.println(msgOut);
            out.writeUTF(msgOut);
        }
        out.flush();
    }

    public void sendFile(String[] args) throws IOException {
        String path = args[2];
//        System.out.println(path);
        File file = new File(path);
        if (file.exists()) {
            System.out.println("Sent " + file.getPath());
            String msgOut = userName + "--> " + "-f " + file.getName();
            out.writeUTF(msgOut); // send fileName
            FileInputStream fileIn = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            fileIn.read(buffer);
            out.writeInt(buffer.length);
            out.write(buffer); // send file
        } else {
            System.out.println("File doesn't exist");
        }
    }

    public void receiveMessage() throws IOException {
        String msgIn = in.readUTF();
        String[] args = msgIn.split(" ", 3);
        // if(msgIn starts with "-f ", that means next part of the msg will contain name of the file)
        if ("-f".equals(args[1]) && args.length == 3) {
            receiveFile(args);   //file receive
            msgIn = args[0] + " " +args[2];
        }
        System.out.println(msgIn);
    }

    public void receiveFile(String[] args) throws IOException {
        File file = new File(folderPath + args[2]);
        FileOutputStream fileOut;
        int fileLength = in.readInt();
        if (fileLength > 0) {
            byte[] buffer = new byte[fileLength];
            in.readFully(buffer, 0, fileLength);
            fileOut = new FileOutputStream(file);
            fileOut.write(buffer, 0, fileLength);
            fileOut.close();
            System.out.println("File received");
            System.out.println("Saved in " + file.getAbsolutePath());
        }
    }
    
    public void run(){
        mkDir();
        new Thread(() -> {
            while (true) {
                try {
                    sendMessage();
                } catch (IOException ex) {
                    Logger.getLogger(ServerNoUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    receiveMessage();
                } catch (IOException ex) {
                    Logger.getLogger(ServerNoUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
}
