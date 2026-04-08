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
import javafx.collections.*;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Patient Management");

        Label searchLabel = new Label("Search Patient by ID or Name");
        TextField searchField = new TextField();
        searchField.setPromptText("Enter patient ID or name");
        searchField.setMaxWidth(Double.MAX_VALUE);

        Label nameLabel = new Label("Patient Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter patient name");
        nameField.setMaxWidth(Double.MAX_VALUE);

        Label ageLabel = new Label("Age");
        TextField ageField = new TextField();
        ageField.setPromptText("Enter age");
        ageField.setMaxWidth(Double.MAX_VALUE);

        Label genderLabel = new Label("Gender");
        RadioButton maleBtn = new RadioButton("Male");
        RadioButton femaleBtn = new RadioButton("Female");
        RadioButton otherBtn = new RadioButton("Other");
        ToggleGroup genderGroup = new ToggleGroup();
        maleBtn.setToggleGroup(genderGroup);
        femaleBtn.setToggleGroup(genderGroup);
        otherBtn.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(10, maleBtn, femaleBtn, otherBtn);

        Label diseaseLabel = new Label("Disease");
        TextField diseaseField = new TextField();
        diseaseField.setPromptText("Enter disease");
        diseaseField.setMaxWidth(Double.MAX_VALUE);

        Label contactLabel = new Label("Contact");
        TextField contactField = new TextField();
        contactField.setPromptText("Enter contact number");
        contactField.setMaxWidth(Double.MAX_VALUE);

        Button addBtn = new Button("Add Patient");
        Button deleteBtn = new Button("Delete Selected");

        UIHelper.stylePrimaryButton(addBtn);
        UIHelper.styleDangerButton(deleteBtn);

        TableView<model.Patient> table = new TableView<>();

        TableColumn<model.Patient, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));

        TableColumn<model.Patient, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));

        TableColumn<model.Patient, Integer> ageCol = new TableColumn<>("Age");
        ageCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("age"));

        TableColumn<model.Patient, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("gender"));

        TableColumn<model.Patient, String> diseaseCol = new TableColumn<>("Disease");
        diseaseCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("disease"));

        TableColumn<model.Patient, String> contactCol = new TableColumn<>("Contact");
        contactCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("contact"));

        table.getColumns().addAll(idCol, nameCol, ageCol, genderCol, diseaseCol, contactCol);
        table.setPrefHeight(320);

        ObservableList<model.Patient> data =
                FXCollections.observableArrayList(new service.PatientService().getPatients());

        FilteredList<model.Patient> filteredData = new FilteredList<>(data, b -> true);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filteredData.setPredicate(patient -> {
                if (newVal == null || newVal.isEmpty()) {
                    return true;
                }

                String keyword = newVal.toLowerCase();

                return String.valueOf(patient.getId()).contains(keyword)
                        || patient.getName().toLowerCase().contains(keyword);
            });
        });

        SortedList<model.Patient> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        addBtn.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                String ageText = ageField.getText().trim();
                String disease = diseaseField.getText().trim();
                String contact = contactField.getText().trim();

                RadioButton selectedGenderBtn = (RadioButton) genderGroup.getSelectedToggle();
                String gender = selectedGenderBtn == null ? "" : selectedGenderBtn.getText();

                if (name.isEmpty() || ageText.isEmpty() || gender.isEmpty() || disease.isEmpty() || contact.isEmpty()) {
                    UIHelper.showWarning("Validation Error", "Please fill all patient fields.");
                    return;
                }

                int age = Integer.parseInt(ageText);
                if (age <= 0) {
                    UIHelper.showWarning("Validation Error", "Age must be greater than 0.");
                    return;
                }

                service.PatientService ps = new service.PatientService();
                ps.registerPatient(name, age, gender, disease, contact);

                data.setAll(ps.getPatients());

                nameField.clear();
                ageField.clear();
                diseaseField.clear();
                contactField.clear();
                genderGroup.selectToggle(null);

                UIHelper.showInfo("Success", "Patient added successfully.");

            } catch (NumberFormatException ex) {
                UIHelper.showError("Invalid Input", "Age must be a valid number.");
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to add patient.");
            }
        });

        deleteBtn.setOnAction(e -> {
            try {
                model.Patient selected = table.getSelectionModel().getSelectedItem();

                if (selected == null) {
                    UIHelper.showWarning("No Selection", "Please select a patient to delete.");
                    return;
                }

                service.PatientService ps = new service.PatientService();
                ps.deletePatient(selected.getId());
                data.setAll(ps.getPatients());

                UIHelper.showInfo("Deleted", "Patient deleted successfully.");

            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Error", "Failed to delete patient.");
            }
        });

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,
                nameLabel, nameField,
                ageLabel, ageField,
                genderLabel, genderBox,
                diseaseLabel, diseaseField,
                contactLabel, contactField,
                addBtn, deleteBtn,
                searchLabel, searchField,
                table
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 860, 760);
        stage.setTitle("Patient Management");
        stage.setScene(scene);
        stage.show();
    }
}