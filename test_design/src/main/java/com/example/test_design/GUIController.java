package com.example.test_design;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GUIController {

                                                //@FXML
    @FXML
    private TextField messageField;

    @FXML
    private VBox messageArea;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox friendList;

    @FXML
    private Label friendName;

    @FXML
    private Label friendStatus;


                                                //BAZA

    ClientController clientController;


    HBox chattingNow = new HBox();



    HashMap<HBox, String> friendCellConnect = new HashMap<>();


    public GUIController(ClientController clientController) {
        this.clientController = clientController;
        clientController.setController(this);
    }

    public void initialize(){

                                    // srcollpane always to bottom
        messageArea.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
             scrollPane.setVvalue((Double) newValue);
            }
        });
    }

    @FXML
    protected void sendMessageArea() {
        String message = messageField.getText();
        message = message.trim();
        if (!message.isEmpty()) {
            messageField.clear();
            clientController.sendText(message);
            createMessageBoxOutgoing(message);
        }
    }

    public void addToFrinedList(Friend friend) {

                                                                                                    //initialization
        HBox friendCell = new HBox();
        ImageView friendPhoto = new ImageView();
        VBox friendInfo = new VBox();
        Label friendNickname = new Label();
        Label lastMessage = new Label();


                                                                                                  //friendCellSetting
        friendCell.setPadding(new Insets(5 ,10 ,5 ,10));
                                                                                                  //friendNicknameSetting


        friendNickname.setText(friend.getNickname());
        friendNickname.setStyle("-fx-font-size: 20px;  " +
                "-fx-font-weight: bold;");
        friendNickname.setTextFill(Color.WHITE);
                                                                                            //lastMessageSetting

        lastMessage.setStyle("-fx-font-size: 16px");
        lastMessage.setTextFill(Color.rgb(157 , 167 , 167));
        lastMessage.setText(friend.getLastSendMessage());
                                                                                            //friendPhotoSetting

        friendPhoto.setImage(new Image(getClass().getResourceAsStream("/img/gomer.png")));
        friendPhoto.setFitHeight(64);
        friendPhoto.setFitWidth(64);

                                                                                             //friendInfoSetting

        friendInfo.setPadding(new Insets(0,0,0,5));
                                                                                                //friendCellSetting

        friendCell.setPadding(new Insets(15,0,15,10));


                                                                                                //construct

        friendInfo.getChildren().add(friendNickname);
        friendInfo.getChildren().add(lastMessage);
        friendCell.getChildren().add(friendPhoto);
        friendCell.getChildren().add(friendInfo);


        friendCellConnect.put(friendCell , friend.key);  // add to map


        friendCell.setOnMouseClicked(mouseEvent -> { 
            chattingNow.setStyle("-fx-background-color: #636566;");                                            // if clicked on the Cell
            System.out.println(friendCellConnect.get(friendCell));
            clientController.chattingFriend=friendCellConnect.get(friendCell);
            friendName.setText(friend.getNickname());
            chattingNow=friendCell;
            friendCell.setStyle("-fx-background-color: #4f4e4e;");
            friendStatus.setText("online");

        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                friendList.getChildren().add(friendCell);
            }
        });
    }

    private void createMessageBoxOutgoing(String message){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(10 , 10 , 5, 30));
  //      ImageView profilePhoto = new ImageView(); // add ProfilePhoto
        Label content = new Label(message);
        content.setStyle("-fx-background-color: #50C984;" +
                "-fx-background-radius: 20px; " +
                "-fx-font-size: 20;");
        content.setPadding(new Insets(5 , 10 , 5 , 10));
        content.setAlignment(Pos.CENTER);
        hBox.getChildren().add(content);
        messageArea.getChildren().add(hBox);
    }

    public void createMessageBoxIncoming(String message){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(10 , 10 , 5, 30));
        //      ImageView profilePhoto = new ImageView(); // add ProfilePhoto
        Label content = new Label(message);
        content.setStyle("-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 20px; " +
                "-fx-font-size: 20;");
        content.setPadding(new Insets(5 , 10 , 5 , 10));
        content.setAlignment(Pos.CENTER);
        hBox.getChildren().add(content);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageArea.getChildren().add(hBox);
            }
        });
    }
}