package com.example.demo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StudentController {

    Student currentStudent = DataBase.getOneStudent();

    @FXML
    private Label userName;
    @FXML
    private Label testLabel;
    @FXML
    private TableView<Subject> subjectTable;
    @FXML
    private TableColumn<Subject, String> subjectName;
    @FXML
    private TableColumn<Subject, Integer> lab1;
    @FXML
    private TableColumn<Subject, Integer> lab2;
    @FXML
    private TableColumn<Subject, Integer> lab3;
    @FXML
    private TableColumn<Subject, Integer> lab4;
    @FXML
    private TableColumn<Subject, Integer> lab5;
    @FXML
    private TableColumn<Subject, Double> average;


    @FXML
    private void initialize(){
        List <Subject> showSubjects = currentStudent.subjects;
        userName.setText(currentStudent.name);
        ObservableList<Subject> subjectsObservableList = FXCollections.observableList(showSubjects);
        subjectName.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
        lab1.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark1"));
        lab2.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark2"));
        lab3.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark3"));
        lab4.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark4"));
        lab5.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark5"));
        average.setCellValueFactory(new PropertyValueFactory<Subject, Double>("average"));
        subjectTable.setItems(subjectsObservableList);
    }


    @FXML
    private void test() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("test.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Test - " + currentStudent.name);
        stage.show();
    }

}