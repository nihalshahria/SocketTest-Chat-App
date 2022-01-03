/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.experimental;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author nihalshahria
 */
public class Test {

    BufferedReader in;

    public void start() throws IOException {
        in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String arg = in.readLine();
            String[] args = arg.split(" ", 2);
            System.out.println(arg);
            for (String str : args) {
                System.out.println(str);
            }
            if (args[0].equals("-f")) {
                File file = new File("/home/nihalshahria/test.txt");
                if (file.exists()) {
                    System.out.println("exists");
                } else {
                    System.out.println("doesn't exist");
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        System.out.println(args);
        System.out.println(System.getProperty("user.name"));
        String userName = System.getProperty("user.name");
        String folderPath = "/home/" + userName + "/SocketApp/Server/";
        File dir = new File(folderPath);
        dir.mkdirs();
//        new Test().start();
    }

}
