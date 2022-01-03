package com.mycompany.sockettest;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class User03 extends JFrame {

    private static final Logger logger = Logger.getLogger("USER-2");
    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    JFileChooser fileChooser;
    File file;
    int returnVal = 10;

    public User03() {
        initComponents();
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }
    
    public static void main(String args[]) {
        User03 client = new User03();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                client.setVisible(true);
            }
        });
        try {
            client.start(User01.port);
        } catch (IOException ex) {
            Logger.getLogger(User01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start(int port) throws IOException {
        socket = new Socket("localhost", port);
        logger.info("Connected to " + socket.getRemoteSocketAddress());
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        while(true){
            String msgIn = "User 01: " + in.readUTF();
            msgArea.append(msgIn + "\n");
            logger.info("Received data from client: " + msgIn);
            
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
                .addComponent(attachFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
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
            logger.info("Written to server: " + msgOut);
            msgArea.append("Me: " + msgOut + "\n");
        } catch (IOException ex) {
            Logger.getLogger(User01.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_SendBtnActionPerformed

    private void attachFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachFileBtnActionPerformed
        // TODO add your handling code here:
        returnVal = fileChooser.showOpenDialog(User03.this);
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JTextArea msgText;
    // End of variables declaration//GEN-END:variables
}
