package com.example.test_design;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends Thread {
    private Socket socket;
    private PrintWriter writer;
    private GUIController guiController;
    private String clientName;
    List<Friend> friends = new ArrayList<>();
    String chattingFriend = null;

    public ClientController(String address, int port) {
        try {
            this.socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setController(GUIController guiController){
        this.guiController = guiController;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            this.writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null) {
                String command = message.substring(0,2);
                String content = message.substring(2);
                System.out.println(message);
                switch(command) {
                    case "IM" -> guiController.createMessageBoxIncoming(content); // InputMessage
                    case "NF" -> newFriend(content);                                       //New Friend
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(String name){
        clientName = name;
        String command = "LO";
        String text = command + name;
        sendToServer(text);
    }

    public void newFriend(String friendInfo){
        String[] a = friendInfo.split(" ");
        String nickname = a[0];
        String key = a[1];
        Friend friend = new Friend(nickname , key);
        friends.add(friend);
        guiController.addToFrinedList(friend);
    }


    public void friendCellChosen(){

    }

    public void sendText(String message){
        message = message.trim();
        if (!message.isEmpty()) {
            String command = "ME";
            String text = command + chattingFriend + message;
            sendToServer(text);
        }
    }

    public void sendToServer(String text) {
        writer.println(text);
    }



}
