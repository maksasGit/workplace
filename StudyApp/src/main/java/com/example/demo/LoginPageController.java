package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private TextField nicknameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    private VBox scene;


    @FXML
    public void submit() throws IOException {
        String fxml = DataBase.LogIn(nicknameField.getText() , passwordField.getText());
        if (fxml.equals("null")) {
            passwordField.clear();
            nicknameField.clear();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.setTitle(fxml.split("\\.")[0] + " " + nicknameField.getText());
            stage.show();
            stage = (Stage) scene.getScene().getWindow();
            stage.close();
        }
    }






}
