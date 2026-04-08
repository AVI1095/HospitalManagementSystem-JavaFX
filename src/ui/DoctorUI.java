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
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DoctorUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Doctor Management & Prescription");

        Label nameLabel = new Label("Doctor Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter doctor name");
        nameField.setMaxWidth(Double.MAX_VALUE);

        Label specLabel = new Label("Specialization");
        TextField specField = new TextField();
        specField.setPromptText("Enter specialization");
        specField.setMaxWidth(Double.MAX_VALUE);

        Label contactLabel = new Label("Contact");
        TextField contactField = new TextField();
        contactField.setPromptText("Enter contact");
        contactField.setMaxWidth(Double.MAX_VALUE);

        Button addBtn = new Button("Add Doctor");
        Button deleteBtn = new Button("Delete Selected");
        Button loadAppointmentsBtn = new Button("Load Assigned Appointments");
        Button savePrescriptionBtn = new Button("Save Prescription");

        UIHelper.stylePrimaryButton(addBtn);
        UIHelper.styleDangerButton(deleteBtn);
        UIHelper.styleSecondaryButton(loadAppointmentsBtn);
        UIHelper.stylePrimaryButton(savePrescriptionBtn);

        TableView<model.Doctor> table = new TableView<>();

        TableColumn<model.Doctor, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<model.Doctor, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));

        TableColumn<model.Doctor, String> specCol = new TableColumn<>("Specialization");
        specCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("specialization"));

        TableColumn<model.Doctor, String> contactCol = new TableColumn<>("Contact");
        contactCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("contact"));

        table.getColumns().addAll(idCol, nameCol, specCol, contactCol);
        table.setPrefHeight(220);

        Label workingDoctorLabel = new Label("Select Working Doctor");
        ComboBox<model.Doctor> doctorSelectBox = new ComboBox<>();
        doctorSelectBox.setMaxWidth(Double.MAX_VALUE);

        Label appointmentLabel = new Label("Assigned Appointment");
        ComboBox<model.Appointment> appointmentBox = new ComboBox<>();
        appointmentBox.setMaxWidth(Double.MAX_VALUE);

        Label patientLabel = new Label("Patient");
        ComboBox<model.Patient> patientBox = new ComboBox<>();
        patientBox.setDisable(true);
        patientBox.setMaxWidth(Double.MAX_VALUE);

        Label medicinesLabel = new Label("Medicines (comma separated)");
        TextField medicinesField = new TextField();
        medicinesField.setPromptText("Example: Paracetamol,Cetirizine");
        medicinesField.setMaxWidth(Double.MAX_VALUE);

        Label testsLabel = new Label("Tests (comma separated)");
        TextField testsField = new TextField();
        testsField.setPromptText("Example: CBC,XRay");
        testsField.setMaxWidth(Double.MAX_VALUE);

        Label remarksLabel = new Label("Remarks");
        TextField remarksField = new TextField();
        remarksField.setPromptText("Enter remarks");
        remarksField.setMaxWidth(Double.MAX_VALUE);

        Label followUpLabel = new Label("Follow-up Date");
        DatePicker followUpPicker = new DatePicker();
        followUpPicker.setMaxWidth(Double.MAX_VALUE);

        TextArea output = new TextArea();
        output.setPromptText("Assigned appointments and patient details");
        output.setPrefHeight(180);
        output.setMaxWidth(Double.MAX_VALUE);

        addBtn.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String spec = specField.getText().trim();
                String contact = contactField.getText().trim();

                if (name.isEmpty() || spec.isEmpty() || contact.isEmpty()) {
                    UIHelper.showWarning("Validation Error", "Please fill all doctor fields.");
                    return;
                }

                service.DoctorService ds = new service.DoctorService();
                ds.addDoctor(name, spec, contact);

                loadDoctors(table, doctorSelectBox);

                nameField.clear();
                specField.clear();
                contactField.clear();

                UIHelper.showInfo("Success", "Doctor added successfully.");

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to add doctor.");
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                model.Doctor selected = table.getSelectionModel().getSelectedItem();

                if (selected == null) {
                    UIHelper.showWarning("No Selection", "Please select a doctor to delete.");
                    return;
                }

                service.DoctorService ds = new service.DoctorService();
                ds.deleteDoctor(selected.getId());
                loadDoctors(table, doctorSelectBox);

                UIHelper.showInfo("Deleted", "Doctor deleted successfully.");

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to delete doctor.");
            }
        });

        loadAppointmentsBtn.setOnAction(e -> {
            try {
                model.Doctor selectedDoctor = doctorSelectBox.getValue();

                if (selectedDoctor == null) {
                    UIHelper.showWarning("Validation Error", "Please select a doctor first.");
                    return;
                }

                service.AppointmentService as = new service.AppointmentService();
                java.util.List<model.Appointment> appointments =
                        as.getAppointmentsByDoctor(selectedDoctor.getId());

                appointmentBox.setItems(FXCollections.observableArrayList(appointments));

                output.clear();
                for (model.Appointment a : appointments) {
                    model.Patient p = new service.PatientService().getPatient(a.getPatientId());

                    if (p != null) {
                        output.appendText(
                                "Appointment ID: " + a.getId() +
                                " | Patient: " + p.getName() +
                                " | Age: " + p.getAge() +
                                " | Disease: " + p.getDisease() +
                                " | Date: " + a.getDate() +
                                " | Status: " + a.getStatus() +
                                "\n"
                        );
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Could not load assigned appointments.");
            }
        });

        appointmentBox.setOnAction(e -> {
            try {
                model.Appointment selectedAppointment = appointmentBox.getValue();
                if (selectedAppointment != null) {
                    model.Patient p = new service.PatientService().getPatient(selectedAppointment.getPatientId());
                    if (p != null) {
                        patientBox.setItems(FXCollections.observableArrayList(p));
                        patientBox.setValue(p);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        savePrescriptionBtn.setOnAction(e -> {
            try {
                model.Doctor doctor = doctorSelectBox.getValue();
                model.Appointment appointment = appointmentBox.getValue();
                model.Patient patient = patientBox.getValue();

                if (doctor == null || appointment == null || patient == null) {
                    UIHelper.showWarning("Validation Error", "Select doctor, appointment, and patient.");
                    return;
                }

                String medicines = medicinesField.getText().trim();
                String tests = testsField.getText().trim();
                String remarks = remarksField.getText().trim();

                if (followUpPicker.getValue() == null) {
                    UIHelper.showWarning("Validation Error", "Please select follow-up date.");
                    return;
                }

                String followUpDate = followUpPicker.getValue().toString();

                service.PrescriptionService ps = new service.PrescriptionService();
                ps.savePrescription(
                        appointment.getId(),
                        patient.getId(),
                        doctor.getId(),
                        medicines,
                        tests,
                        remarks,
                        followUpDate
                );

                medicinesField.clear();
                testsField.clear();
                remarksField.clear();
                followUpPicker.setValue(null);

                UIHelper.showInfo("Success", "Prescription saved successfully.");

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to save prescription.");
            }
        });

        loadDoctors(table, doctorSelectBox);

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,

                nameLabel, nameField,
                specLabel, specField,
                contactLabel, contactField,
                addBtn, deleteBtn,
                table,

                workingDoctorLabel, doctorSelectBox,
                loadAppointmentsBtn,
                appointmentLabel, appointmentBox,
                patientLabel, patientBox,
                medicinesLabel, medicinesField,
                testsLabel, testsField,
                remarksLabel, remarksField,
                followUpLabel, followUpPicker,
                savePrescriptionBtn,
                output
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 900, 820);
        stage.setTitle("Doctor Module");
        stage.setScene(scene);
        stage.show();
    }

    private void loadDoctors(TableView<model.Doctor> table, ComboBox<model.Doctor> doctorSelectBox) {
        service.DoctorService ds = new service.DoctorService();
        ObservableList<model.Doctor> doctors = FXCollections.observableArrayList(ds.getDoctors());
        table.setItems(doctors);
        doctorSelectBox.setItems(doctors);
    }
}