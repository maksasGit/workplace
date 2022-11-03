package com.example.demo;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TeacherController {

    List < Student> students = DataBase.readFromDB();

    @FXML
    private Label userName;

    @FXML
    private ListView studentsList;

    @FXML
    private Label studentName;

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


    String currentNameStudent = null;

    @FXML
    private void initialize(){

        showStudents();
        studentsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                currentNameStudent = String.valueOf(studentsList.getSelectionModel().getSelectedItems());
                showStudentContent();
            }
        });
    }

    public void showStudents(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                studentsList.getItems().clear();
                for (Student student : students) {
                    studentsList.getItems().add(student.name);
                }
            }
        });
    }

    public void showStudentContent() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Student currentStudent = null;
                if (!currentNameStudent.isEmpty()) {
                    for (Student student : students) {
                        if (currentNameStudent.substring(1, currentNameStudent.length() - 1).equals(student.name)) {
                            currentStudent = student;
                        }
                    }
                }
                List <Subject> showSubjects = currentStudent.subjects;
                ObservableList<Subject> subjectsObservableList = FXCollections.observableList(showSubjects);
                studentName.setText(currentStudent.name);
                subjectName.setCellValueFactory(new PropertyValueFactory<Subject, String>("name"));
                lab1.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark1"));
                lab2.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark2"));
                lab3.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark3"));
                lab4.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark4"));
                lab5.setCellValueFactory(new PropertyValueFactory<Subject, Integer>("mark5"));
                average.setCellValueFactory(new PropertyValueFactory<Subject, Double>("average"));
                subjectTable.setItems(subjectsObservableList);
            }
        });
    }

}