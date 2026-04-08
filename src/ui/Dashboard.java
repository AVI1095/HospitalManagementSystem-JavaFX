/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author adityaingle
 */
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Session;

public class Dashboard {

    private static String role;

    public static void setRole(String userRole) {
        role = userRole;
    }

    public void start(Stage stage) {

        Label titleLabel = UIHelper.sectionTitle("Hospital Dashboard");
        Label roleLabel = new Label("Logged in as: " + Session.getUsername() + " (" + role + ")");
        roleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #334155;");

        Button patientBtn = new Button("Patient Module");
        Button doctorBtn = new Button("Doctor Module");
        Button appointmentBtn = new Button("Appointment Module");
        Button billingBtn = new Button("Billing Module");
        Button inventoryBtn = new Button("Inventory Module");
        Button auditBtn = new Button("Audit Logs");
        Button statsBtn = new Button("Admin Stats");
        Button expenseBtn = new Button("Expenses Module");
        Button logoutBtn = new Button("Logout");

        UIHelper.stylePrimaryButton(patientBtn);
        UIHelper.stylePrimaryButton(doctorBtn);
        UIHelper.stylePrimaryButton(appointmentBtn);
        UIHelper.stylePrimaryButton(billingBtn);
        UIHelper.stylePrimaryButton(inventoryBtn);
        UIHelper.stylePrimaryButton(auditBtn);
        UIHelper.stylePrimaryButton(statsBtn);
        UIHelper.stylePrimaryButton(expenseBtn);
        UIHelper.styleDangerButton(logoutBtn);

        patientBtn.setOnAction(e -> {
            try {
                new PatientUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Patient Module.");
            }
        });

        doctorBtn.setOnAction(e -> {
            try {
                new DoctorUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Doctor Module.");
            }
        });

        appointmentBtn.setOnAction(e -> {
            try {
                new AppointmentUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Appointment Module.");
            }
        });

        billingBtn.setOnAction(e -> {
            try {
                new BillingUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Billing Module.");
            }
        });

        inventoryBtn.setOnAction(e -> {
            try {
                new InventoryUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Inventory Module.");
            }
        });

        auditBtn.setOnAction(e -> {
            try {
                new AuditLogUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Audit Logs.");
            }
        });

        statsBtn.setOnAction(e -> {
            try {
                new AdminStatsUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Admin Stats.");
            }
        });

        expenseBtn.setOnAction(e -> {
            try {
                new ExpenseUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Open Failed", "Could not open Expense Module.");
            }
        });

        logoutBtn.setOnAction(e -> {
            try {
                Session.clear();
                new LoginUI().start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
                UIHelper.showError("Logout Failed", "Could not return to login screen.");
            }
        });

        patientBtn.setVisible(false);
        doctorBtn.setVisible(false);
        appointmentBtn.setVisible(false);
        billingBtn.setVisible(false);
        inventoryBtn.setVisible(false);
        auditBtn.setVisible(false);
        statsBtn.setVisible(false);
        expenseBtn.setVisible(false);

        if (role != null) {
            switch (role.toLowerCase()) {
                case "admin":
                    patientBtn.setVisible(true);
                    doctorBtn.setVisible(true);
                    appointmentBtn.setVisible(true);
                    billingBtn.setVisible(true);
                    inventoryBtn.setVisible(true);
                    auditBtn.setVisible(true);
                    statsBtn.setVisible(true);
                    expenseBtn.setVisible(true);
                    break;

                case "doctor":
                    patientBtn.setVisible(true);
                    doctorBtn.setVisible(true);
                    appointmentBtn.setVisible(true);
                    break;

                case "receptionist":
                    patientBtn.setVisible(true);
                    appointmentBtn.setVisible(true);
                    billingBtn.setVisible(true);
                    break;

                case "accounts":
                    billingBtn.setVisible(true);
                    expenseBtn.setVisible(true);
                    break;
            }
        }

        VBox layout = UIHelper.createRoot();
        layout.setAlignment(Pos.TOP_CENTER);

        layout.getChildren().addAll(
                titleLabel,
                roleLabel,
                patientBtn,
                doctorBtn,
                appointmentBtn,
                billingBtn,
                inventoryBtn,
                auditBtn,
                statsBtn,
                expenseBtn,
                logoutBtn
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 460, 600);
        stage.setTitle("Hospital Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}