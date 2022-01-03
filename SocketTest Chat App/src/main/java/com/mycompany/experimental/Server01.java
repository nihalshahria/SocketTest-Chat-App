package com.mycompany.experimental;

import com.mycompany.sockettest.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Server01 extends JFrame {

    private static final Logger logger = Logger.getLogger("USER-01");
    public static final int port = 1234;
    ServerSocket serverSocket;
//    Socket clientSocket;
//    DataInputStream in;
    DataOutputStream out;

    JFileChooser fileChooser;
    File file;
    int returnVal = 10;

    public Server01() {
        initComponents();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public static void main(String args[]) throws IOException {
        Server01 server = new Server01();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                server.setVisible(true);
            }
        });
        server.start();
    }

    private void start() throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Listening to: " + port);
        while (true) {
            new ClientHandlar(serverSocket.accept()).start();
//            Socket clientSocket = serverSocket.accept();
        }
    }

//    private void processSocketConnection(Socket clientSocket) throws IOException {
//        in = new DataInputStream(clientSocket.getInputStream());
//        out = new DataOutputStream(clientSocket.getOutputStream());
//        while (true) {
//            String msgIn = "User 02: " + in.readUTF();
//            msgArea.append(msgIn + "\n");
//            logger.info("Received data from client: " + msgIn);
//        }
//    }
    private class ClientHandlar extends Thread {
        private Socket clientSocket;
        DataInputStream in;
        DataOutputStream out;

        public ClientHandlar(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            logger.info("New socket connection to: " + clientSocket.getRemoteSocketAddress());
            try {
                processSocketConnection(clientSocket);
                in.close();
                out.close();
                clientSocket.close();
            } catch (Exception ex) {
                Logger.getLogger(Server01.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        private void processSocketConnection(Socket clientSocket) throws IOException {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            while (true) {
                String msgIn = "User 02: " + in.readUTF();
                msgArea.append(msgIn + "\n");
                logger.info("Received data from client: " + msgIn);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        SendBtn = new javax.swing.JButton();
        attachFileBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        msgText = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SendBtn.setText("Send");
        SendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendBtnActionPerformed(evt);
            }
        });

        attachFileBtn.setText("Attach File");
        attachFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachFileBtnActionPerformed(evt);
            }
        });

        msgText.setColumns(20);
        msgText.setLineWrap(true);
        msgText.setRows(3);
        msgText.setTabSize(4);
        msgText.setToolTipText("");
        msgText.setWrapStyleWord(true);
        jScrollPane3.setViewportView(msgText);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(attachFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SendBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(SendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(attachFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        msgArea.setEditable(false);
        msgArea.setColumns(20);
        msgArea.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        msgArea.setLineWrap(true);
        msgArea.setRows(5);
        msgArea.setWrapStyleWord(true);
        msgArea.setInheritsPopupMenu(true);
        jScrollPane1.setViewportView(msgArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendBtnActionPerformed
        String msgOut = "";
        msgOut = msgText.getText().trim();
        msgText.setText("");
        try {
            out.writeUTF(msgOut);
            logger.info("Written to socket: " + msgOut);
            msgArea.append("Me: " + msgOut + "\n");
        } catch (IOException ex) {
            Logger.getLogger(Server01.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SendBtnActionPerformed

    private void attachFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachFileBtnActionPerformed
        returnVal = fileChooser.showOpenDialog(Server01.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            msgArea.append("Selected: " + file.getPath() + "." + "\n");
        } else {
            msgArea.append("Nothing was selected by user." + "\n");
        }
        msgArea.setCaretPosition(msgArea.getDocument().getLength());
    }//GEN-LAST:event_attachFileBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SendBtn;
    private javax.swing.JButton attachFileBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JTextArea msgText;
    // End of variables declaration//GEN-END:variables
}
