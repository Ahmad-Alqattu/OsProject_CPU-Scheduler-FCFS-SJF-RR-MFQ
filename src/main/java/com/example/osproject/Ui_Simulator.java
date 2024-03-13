package com.example.osproject;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.osproject.Main_Scheduler.runSimulation;

public class Ui_Simulator extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    GridPane gridPane = new GridPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(" UI CPU Algorithm Simulator");

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // TextFields for Iteration 1-4 with labels and default values
        TextField[] iterationFields = new TextField[4];
        for (int i = 1; i <= 4; i++) {
            Label iterationLabel = new Label("Iteration " + i);
            gridPane.add(iterationLabel, i - 1, 0);

            iterationFields[i - 1] = new TextField(String.valueOf(((Double) Math.pow(10, i + 1)).intValue()));
            gridPane.add(iterationFields[i - 1], i - 1, 1);
        }

        // TextFields for Range (First and Second) with labels and default values
        Label firstRangeLabel = new Label("Birst First Range");
        Label secondRangeLabel = new Label("Second Range");
        Label ss = new Label("Second Range");

        TextField sss = new TextField("5");
        TextField firstRangeField = new TextField("5");
        TextField secondRangeField = new TextField("100");

        gridPane.add(firstRangeLabel, 0, 2);
        gridPane.add(firstRangeField, 1, 2);
        gridPane.add(secondRangeLabel, 2, 2);
        gridPane.add(secondRangeField, 3, 2);

        // Label and TextField for Process Num on a new line
        Label processNumLabel = new Label("Process Num");
        Label RRLabel = new Label("RR slice time");
        TextField RRField = new TextField("20");

        TextField processNumField = new TextField("8");
        gridPane.add(processNumLabel, 0, 3);
        gridPane.add(processNumField, 1, 3);
        gridPane.add(RRLabel, 2, 3);
        gridPane.add(RRField, 3, 3);


        // Adding 4 tables with 5 columns and 3 rows (smaller height) arranged vertically, spanning 4 columns
        for (int i = 0; i < 4; i++) {
            TableView<List<String>> table = new TableView<>();
            table.setPrefHeight(115);
            table.getStyleClass().add("table-view");
            for (TableColumn<List<String>, ?> column : table.getColumns()) {
                column.getStyleClass().add("table-column");
            }

            // Set preferred width for columns
            for (int j = 0; j < 5; j++) {
                TableColumn<List<String>, String> column = new TableColumn<>("iter" + (j + 1));
                column.setPrefWidth(85); // Set preferred width
                table.getColumns().add(column);

            }

            for (int k = 0; k < 5; k++) {
                table.getItems().add(new ArrayList<>());
            }

            gridPane.add(table, 0, 4 + i, 4, 1);
        }

        // Button for Run
        Button runButton = new Button("Run");
        runButton.getStyleClass().add("button");
        runButton.setPadding(new Insets(5, 5, 5, 5));
        TextField textField = new TextField("20");

        Label time_order = new Label("arrive counter");
        Label ahmad = new Label("Ahmad Luay Al-qatow Project: 1193000");


        CheckBox checkBox = new CheckBox("random");

// Add the TextField, Label, and CheckBox to the GridPane
        gridPane.add(runButton, 3, 8, 4, 2);
        gridPane.add(textField, 1, 8); // Adjust the column index as needed
        gridPane.add(time_order, 0, 8); // Adjust the column index as needed
        gridPane.add(checkBox, 2, 8); // Adjust the column index as needed
        gridPane.add(ahmad, 0, 9,3,2); // Adjust the column index as needed

        // Set some color and padding
//        gridPane.setStyle("-fx-background-color: #E0E0E0;");
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        BorderPane border = new BorderPane();
        border.setCenter(gridPane);
        // Create the scene and show the stage
        Scene scene = new Scene(border, 500, 705);

        scene.getStylesheets().add(("styles.css")); // Add external CSS
        primaryStage.setScene(scene);
        // OnAction handler for the Run button
        runButton.setOnAction(e -> {
            // Get values from TextFields and convert them to integers
            int[] iterations = new int[4];
            for (int i = 0; i < 4; i++) {
                iterations[i] = Integer.parseInt(iterationFields[i].getText());
                System.out.println(iterations[i]);
            }

            int firstRange = Integer.parseInt(firstRangeField.getText());
            int secondRange = Integer.parseInt(secondRangeField.getText());
            int processNum = Integer.parseInt(processNumField.getText());
            int rrNum = Integer.parseInt(RRField.getText());
            int order = Integer.parseInt(RRField.getText());


            // Run simulations and update tables
            updateTable("FCFS", iterations, processNum, firstRange, secondRange, 16, rrNum,order,checkBox.isSelected());
            updateTable("SJF", iterations, processNum, firstRange, secondRange, 17, rrNum,order,checkBox.isSelected());
            updateTable("RR", iterations, processNum, firstRange, secondRange, 18, rrNum,order,checkBox.isSelected());
            updateTable("MFQ", iterations, processNum, firstRange, secondRange, 19, rrNum,order,checkBox.isSelected());
        });

        primaryStage.setResizable(false);

        primaryStage.show();

    }

    private void updateTable(String algorithm, int[] iterations, int processes, int firstRange, int secondRange, int rowIndex, int rrNum,int arrival,boolean random) {
        TableView<List<String>> table = (TableView<List<String>>) gridPane.getChildren().get(rowIndex);

        // Run simulation and get results
        ArrayList<double[]> results = runSimulation(algorithm, iterations, processes, firstRange, secondRange, rrNum,arrival,random);

        // Clear the table before adding new columns
        table.getColumns().clear();

        // Add columns for each iteration
        for (int i = 0; i < iterations.length + 1; i++) {
            final int index = i;

            TableColumn<List<String>, String> iterationColumn = new TableColumn<>(String.valueOf(i == 0 ? algorithm : iterations[i - 1]));
            iterationColumn.setPrefWidth(85);
            if (i == 0) {
                iterationColumn.getStyleClass().add("first");

            } else {
                iterationColumn.getStyleClass().add("table-column");

            }


            iterationColumn.setCellValueFactory(cellData -> {

                return new SimpleStringProperty(cellData.getValue().get(index));
            });
            table.getColumns().add(iterationColumn);
        }

        // Add rows for ATT and AWT
        List<String> attRow = new ArrayList<>();
        List<String> awtRow = new ArrayList<>();
        attRow.add("ATT");
        awtRow.add("AWT");
        for (int i = 0; i < results.get(0).length; i++) {
            attRow.add(String.format("%.2f", results.get(0)[i]));
            awtRow.add(String.format("%.2f", results.get(1)[i]));
        }
        ObservableList<List<String>> data = FXCollections.observableArrayList(attRow, awtRow);
        table.setItems(data);

    }
}
