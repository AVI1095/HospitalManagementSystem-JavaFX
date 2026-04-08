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

public class AppointmentUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Appointment Management");

        Label patientLabel = new Label("Select Patient");
        ComboBox<model.Patient> patientBox = new ComboBox<>();
        patientBox.setMaxWidth(Double.MAX_VALUE);

        Label doctorLabel = new Label("Select Doctor");
        ComboBox<model.Doctor> doctorBox = new ComboBox<>();
        doctorBox.setMaxWidth(Double.MAX_VALUE);

        Label dateLabel = new Label("Appointment Date");
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(Double.MAX_VALUE);

        Button bookBtn = new Button("Book Appointment");
        UIHelper.stylePrimaryButton(bookBtn);

        TableView<model.Appointment> table = new TableView<>();

        TableColumn<model.Appointment, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<model.Appointment, Integer> pidCol = new TableColumn<>("Patient ID");
        pidCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("patientId"));

        TableColumn<model.Appointment, Integer> didCol = new TableColumn<>("Doctor ID");
        didCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("doctorId"));

        TableColumn<model.Appointment, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("date"));

        TableColumn<model.Appointment, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("status"));

        table.getColumns().addAll(idCol, pidCol, didCol, dateCol, statusCol);
        table.setPrefHeight(300);

        service.PatientService ps = new service.PatientService();
        service.DoctorService ds = new service.DoctorService();

        patientBox.setItems(FXCollections.observableArrayList(ps.getPatients()));
        doctorBox.setItems(FXCollections.observableArrayList(ds.getDoctors()));

        bookBtn.setOnAction(e -> {
            try {
                model.Patient patient = patientBox.getValue();
                model.Doctor doctor = doctorBox.getValue();

                if (patient == null || doctor == null) {
                    UIHelper.showWarning("Validation Error", "Please select both patient and doctor.");
                    return;
                }

                if (datePicker.getValue() == null) {
                    UIHelper.showWarning("Validation Error", "Please select appointment date.");
                    return;
                }

                String date = datePicker.getValue().toString();

                service.AppointmentService as = new service.AppointmentService();
                as.bookAppointment(patient.getId(), doctor.getId(), date);

                loadTable(table);

                patientBox.setValue(null);
                doctorBox.setValue(null);
                datePicker.setValue(null);

                UIHelper.showInfo("Success", "Appointment booked successfully.");

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to book appointment.");
            }
        });

        loadTable(table);

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,
                patientLabel, patientBox,
                doctorLabel, doctorBox,
                dateLabel, datePicker,
                bookBtn,
                table
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 760, 620);
        stage.setTitle("Appointment Management");
        stage.setScene(scene);
        stage.show();
    }

    private void loadTable(TableView<model.Appointment> table) {
        service.AppointmentService as = new service.AppointmentService();
        table.setItems(FXCollections.observableArrayList(as.getAppointments()));
    }
}