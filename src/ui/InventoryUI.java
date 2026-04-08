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

public class InventoryUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Inventory Management");

        Label nameLabel = new Label("Medicine Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter medicine name");
        nameField.setMaxWidth(Double.MAX_VALUE);

        Label qtyLabel = new Label("Quantity");
        TextField quantityField = new TextField();
        quantityField.setPromptText("Enter quantity");
        quantityField.setMaxWidth(Double.MAX_VALUE);

        Label expiryLabel = new Label("Expiry Date");
        DatePicker expiryPicker = new DatePicker();
        expiryPicker.setMaxWidth(Double.MAX_VALUE);

        Label thresholdLabel = new Label("Threshold Quantity");
        TextField thresholdField = new TextField();
        thresholdField.setPromptText("Enter threshold quantity");
        thresholdField.setMaxWidth(Double.MAX_VALUE);

        Button addBtn = new Button("Add Medicine");
        Button deleteBtn = new Button("Delete Selected");
        Button lowStockBtn = new Button("Show Low Stock");
        Button expiryBtn = new Button("Show Expiring Soon");
        Button showAllBtn = new Button("Show All");

        UIHelper.stylePrimaryButton(addBtn);
        UIHelper.styleDangerButton(deleteBtn);
        UIHelper.styleSecondaryButton(lowStockBtn);
        UIHelper.styleSecondaryButton(expiryBtn);
        UIHelper.styleSecondaryButton(showAllBtn);

        TableView<model.Medicine> table = new TableView<>();

        TableColumn<model.Medicine, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<model.Medicine, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));

        TableColumn<model.Medicine, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("quantity"));

        TableColumn<model.Medicine, String> expiryCol = new TableColumn<>("Expiry Date");
        expiryCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("expiryDate"));

        TableColumn<model.Medicine, Integer> thresholdCol = new TableColumn<>("Threshold");
        thresholdCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("thresholdQty"));

        table.getColumns().addAll(idCol, nameCol, qtyCol, expiryCol, thresholdCol);
        table.setPrefHeight(300);

        addBtn.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String qtyText = quantityField.getText().trim();
                String thresholdText = thresholdField.getText().trim();

                if (name.isEmpty() || qtyText.isEmpty() || thresholdText.isEmpty() || expiryPicker.getValue() == null) {
                    UIHelper.showWarning("Validation Error", "Please fill all inventory fields.");
                    return;
                }

                int quantity = Integer.parseInt(qtyText);
                int threshold = Integer.parseInt(thresholdText);
                String expiryDate = expiryPicker.getValue().toString();

                service.MedicineService ms = new service.MedicineService();
                ms.addMedicine(name, quantity, expiryDate, threshold);

                loadAll(table);

                nameField.clear();
                quantityField.clear();
                thresholdField.clear();
                expiryPicker.setValue(null);

                UIHelper.showInfo("Success", "Medicine added successfully.");

            } catch (NumberFormatException ex) {
                UIHelper.showError("Invalid Input", "Quantity and threshold must be numeric.");
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to add medicine.");
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                model.Medicine selected = table.getSelectionModel().getSelectedItem();

                if (selected == null) {
                    UIHelper.showWarning("No Selection", "Please select a medicine to delete.");
                    return;
                }

                service.MedicineService ms = new service.MedicineService();
                ms.deleteMedicine(selected.getId());
                loadAll(table);

                UIHelper.showInfo("Deleted", "Medicine deleted successfully.");

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to delete medicine.");
            }
        });

        lowStockBtn.setOnAction(e -> {
            service.MedicineService ms = new service.MedicineService();
            table.setItems(FXCollections.observableArrayList(ms.getLowStockMedicines()));
        });

        expiryBtn.setOnAction(e -> {
            service.MedicineService ms = new service.MedicineService();
            table.setItems(FXCollections.observableArrayList(ms.getExpiringSoonMedicines()));
        });

        showAllBtn.setOnAction(e -> loadAll(table));

        loadAll(table);

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,
                nameLabel, nameField,
                qtyLabel, quantityField,
                expiryLabel, expiryPicker,
                thresholdLabel, thresholdField,
                addBtn, deleteBtn, lowStockBtn, expiryBtn, showAllBtn,
                table
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 860, 760);
        stage.setTitle("Inventory Module");
        stage.setScene(scene);
        stage.show();
    }

    private void loadAll(TableView<model.Medicine> table) {
        service.MedicineService ms = new service.MedicineService();
        table.setItems(FXCollections.observableArrayList(ms.getMedicines()));
    }
}