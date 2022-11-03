package com.example.test_design;

import javafx.scene.image.Image;

import java.net.Socket;

public class Friend {

    private String nickname;
  //  private Image profilePhoto;
    private int missedMessage;
    private String lastSendMessage;
    private Socket socket;
    private Boolean isChating;
    public String key;


    public Friend(String nickname , String key) {
        this.nickname = nickname;
        this.key = key;
        lastSendMessage = "Null";
        missedMessage = 0;
        isChating = false;
    }

    public String getNickname() {
        return nickname;
    }



    public String getLastSendMessage() {
        return lastSendMessage;
    }

    public void startChatting(){
        missedMessage = 0;
        isChating = true;
    }




}
