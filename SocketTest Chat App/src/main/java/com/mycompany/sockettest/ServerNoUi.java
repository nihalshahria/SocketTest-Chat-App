package com.mycompany.sockettest;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author nihalshahria
 */
public class ServerNoUi {

    public final int port = 6666;
    public String userName;
    public String folderPath;
    private ServerSocket server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader inConsole;

    public static void main(String[] args) throws IOException {
        new ServerNoUi().start();
    }

    public void start() throws IOException {
        server = new ServerSocket(port);
        socket = server.accept();

        mkDir();  // create new folder according to folder path
        resetStreams(); // streams initializations

        // Message send
        new Thread(() -> {
            while (true) {
                try {
                    sendMessage();
                } catch (IOException ex) {
                    Logger.getLogger(ServerNoUi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();

        // Message receive
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

    private boolean mkDir() {
        userName = System.getProperty("user.name");
        folderPath = "/home/" + userName + "/SocketApp/Server/"; //folderPath to save incoming files
        File dir = new File(folderPath);
        if (dir.exists()) {
            return false;
        }
        return dir.mkdirs();
    }

    private void resetStreams() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        inConsole = new BufferedReader(new InputStreamReader(System.in));
    }

    public void sendMessage() throws IOException {
        String msgOut = inConsole.readLine();   //read input from terminal
        String[] args = msgOut.split(" ", 2);
        // if(msgOut starts with "-f ", that means next part of the msg will contain path of the file)

        if ("-f".equals(args[0]) && args.length == 2) {
            sendFile(args);
        } else {
            System.out.println("From Server: " + msgOut);
            out.writeUTF(msgOut);
        }
        out.flush();
    }

    public void sendFile(String[] args) throws IOException {
        String path = args[1];
//        System.out.println(path);
        File file = new File(path);
        if (file.exists()) {
            System.out.println("Sent " + file.getPath());
            out.writeUTF("-f " + file.getName()); // send fileName
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
        String[] args = msgIn.split(" ", 2);
        // if(msgIn starts with "-f ", that means next part of the msg will contain name of the file)
        if ("-f".equals(args[0]) && args.length == 2) {
            receiveFile(args);   //file receive
            msgIn = args[1];
        }
        System.out.println("From Client: " + msgIn);
    }

    public void receiveFile(String[] args) throws IOException {
        File file = new File(folderPath + args[1]);
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
}
