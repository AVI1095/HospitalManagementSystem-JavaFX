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

public class BillingUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Billing Module");

        Label prescriptionLabel = new Label("Select Prescription");
        ComboBox<model.Prescription> prescriptionBox = new ComboBox<>();
        prescriptionBox.setMaxWidth(Double.MAX_VALUE);

        Label consultationLabel = new Label("Consultation Fee");
        TextField consultationFeeField = new TextField();
        consultationFeeField.setPromptText("Enter consultation fee");
        consultationFeeField.setMaxWidth(Double.MAX_VALUE);

        Label testFeeLabel = new Label("Test Fee");
        TextField testFeeField = new TextField();
        testFeeField.setPromptText("Enter test fee");
        testFeeField.setMaxWidth(Double.MAX_VALUE);

        Label medicineFeeLabel = new Label("Medicine Fee");
        TextField medicineFeeField = new TextField();
        medicineFeeField.setPromptText("Enter medicine fee");
        medicineFeeField.setMaxWidth(Double.MAX_VALUE);

        Label paymentLabel = new Label("Payment Method");
        ComboBox<String> paymentMethodBox = new ComboBox<>();
        paymentMethodBox.setItems(FXCollections.observableArrayList("Cash", "Card", "UPI"));
        paymentMethodBox.setPromptText("Select payment method");
        paymentMethodBox.setMaxWidth(Double.MAX_VALUE);

        Label dateLabel = new Label("Billing Date");
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Button generateBtn = new Button("Generate Bill");
        UIHelper.stylePrimaryButton(generateBtn);

        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 13px;");

        TableView<model.Billing> table = new TableView<>();

        TableColumn<model.Billing, Integer> idCol = new TableColumn<>("Bill ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<model.Billing, Integer> patientIdCol = new TableColumn<>("Patient ID");
        patientIdCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("patientId"));

        TableColumn<model.Billing, Double> consultationCol = new TableColumn<>("Consultation");
        consultationCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("consultationFee"));

        TableColumn<model.Billing, Double> testCol = new TableColumn<>("Test");
        testCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("testFee"));

        TableColumn<model.Billing, Double> medicineCol = new TableColumn<>("Medicine");
        medicineCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("medicineFee"));

        TableColumn<model.Billing, Double> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("totalAmount"));

        TableColumn<model.Billing, String> paymentCol = new TableColumn<>("Payment");
        paymentCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("paymentMethod"));

        TableColumn<model.Billing, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

        table.getColumns().addAll(
                idCol, patientIdCol, consultationCol, testCol,
                medicineCol, totalCol, paymentCol, dateCol
        );
        table.setPrefHeight(300);

        service.PrescriptionService ps = new service.PrescriptionService();
        prescriptionBox.setItems(FXCollections.observableArrayList(ps.getAllPrescriptions()));

        generateBtn.setOnAction(e -> {
            try {
                model.Prescription prescription = prescriptionBox.getValue();

                if (prescription == null) {
                    UIHelper.showWarning("Validation Error", "Select a prescription.");
                    return;
                }

                if (consultationFeeField.getText().trim().isEmpty()) {
                    UIHelper.showWarning("Validation Error", "Enter consultation fee.");
                    return;
                }

                if (testFeeField.getText().trim().isEmpty()) {
                    UIHelper.showWarning("Validation Error", "Enter test fee.");
                    return;
                }

                if (medicineFeeField.getText().trim().isEmpty()) {
                    UIHelper.showWarning("Validation Error", "Enter medicine fee.");
                    return;
                }

                if (paymentMethodBox.getValue() == null) {
                    UIHelper.showWarning("Validation Error", "Select a payment method.");
                    return;
                }

                if (datePicker.getValue() == null) {
                    UIHelper.showWarning("Validation Error", "Select billing date.");
                    return;
                }

                double consultationFee = Double.parseDouble(consultationFeeField.getText().trim());
                double testFee = Double.parseDouble(testFeeField.getText().trim());
                double medicineFee = Double.parseDouble(medicineFeeField.getText().trim());

                String paymentMethod = paymentMethodBox.getValue();
                String date = datePicker.getValue().toString();

                service.BillingService bs = new service.BillingService();
                bs.generateBillFromPrescription(
                        prescription.getId(),
                        consultationFee,
                        testFee,
                        medicineFee,
                        paymentMethod,
                        date
                );

                loadTable(table);

                prescriptionBox.setValue(null);
                consultationFeeField.clear();
                testFeeField.clear();
                medicineFeeField.clear();
                paymentMethodBox.setValue(null);
                datePicker.setValue(null);

                statusLabel.setText("Bill generated successfully.");
                UIHelper.showInfo("Success", "Bill generated successfully.");

            } catch (NumberFormatException ex) {
                UIHelper.showError("Invalid Input", "Consultation fee, test fee, and medicine fee must be numeric.");
            } catch (RuntimeException ex) {
                UIHelper.showError("Billing Error", ex.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to generate bill.");
            }
        });

        loadTable(table);

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,
                prescriptionLabel, prescriptionBox,
                consultationLabel, consultationFeeField,
                testFeeLabel, testFeeField,
                medicineFeeLabel, medicineFeeField,
                paymentLabel, paymentMethodBox,
                dateLabel, datePicker,
                generateBtn,
                statusLabel,
                table
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 1000, 760);
        stage.setTitle("Billing Module");
        stage.setScene(scene);
        stage.show();
    }

    private void loadTable(TableView<model.Billing> table) {
        service.BillingService bs = new service.BillingService();
        table.setItems(FXCollections.observableArrayList(bs.getBills()));
    }
}