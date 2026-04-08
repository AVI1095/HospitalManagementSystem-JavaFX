/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author adityaingle
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExpenseUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Expense Management");

        Label descLabel = new Label("Expense Description");
        TextField descField = new TextField();
        descField.setPromptText("Enter expense description");
        descField.setMaxWidth(Double.MAX_VALUE);

        Label amountLabel = new Label("Amount");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");
        amountField.setMaxWidth(Double.MAX_VALUE);

        Label dateLabel = new Label("Expense Date");
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Button addBtn = new Button("Add Expense");
        UIHelper.stylePrimaryButton(addBtn);

        TableView<model.Expense> table = new TableView<>();

        TableColumn<model.Expense, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<model.Expense, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("description"));

        TableColumn<model.Expense, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("amount"));

        TableColumn<model.Expense, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

        table.getColumns().addAll(idCol, descCol, amountCol, dateCol);
        table.setPrefHeight(280);

        addBtn.setOnAction(e -> {
            try {
                String desc = descField.getText().trim();
                String amountText = amountField.getText().trim();

                if (desc.isEmpty() || amountText.isEmpty() || datePicker.getValue() == null) {
                    UIHelper.showWarning("Validation Error", "Please fill all expense fields.");
                    return;
                }

                double amount = Double.parseDouble(amountText);
                String date = datePicker.getValue().toString();

                service.ExpenseService es = new service.ExpenseService();
                es.addExpense(desc, amount, date);

                loadTable(table);

                descField.clear();
                amountField.clear();
                datePicker.setValue(null);

                UIHelper.showInfo("Success", "Expense added successfully.");

            } catch (NumberFormatException ex) {
                UIHelper.showError("Invalid Input", "Amount must be numeric.");
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to add expense.");
            }
        });

        loadTable(table);

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,
                descLabel, descField,
                amountLabel, amountField,
                dateLabel, datePicker,
                addBtn,
                table
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 760, 620);
        stage.setTitle("Expense Module");
        stage.setScene(scene);
        stage.show();
    }

    private void loadTable(TableView<model.Expense> table) {
        service.ExpenseService es = new service.ExpenseService();
        table.setItems(FXCollections.observableArrayList(es.getExpenses()));
    }
}