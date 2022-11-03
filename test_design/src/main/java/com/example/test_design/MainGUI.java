package com.example.test_design;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ClientController thread = new ClientController("localhost" , 5004);
        thread.start();
        FXMLLoader fxmlLoader = new FXMLLoader(GUIController.class.getResource("hello-view.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> new GUIController(thread));
        Scene scene = new Scene(fxmlLoader.load(), 1006, 708);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Login");
        dialog.setHeaderText("Login");
        dialog.setContentText("Please enter your name:");
        Optional<String> result = dialog.showAndWait();
        String login = null;
        if (result.isPresent())  {
            login = result.get();
            thread.login(login);
            stage.setTitle("Chat - " + login);
            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}