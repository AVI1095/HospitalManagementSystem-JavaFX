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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.LoginService;
import util.Session;

public class LoginUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Hospital Management System");
        title.setStyle(
                "-fx-font-size: 24px;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: #1e3a8a;"
        );

        Label subtitle = new Label("Secure Login");
        subtitle.setStyle(
                "-fx-font-size: 14px;" +
                "-fx-text-fill: #475569;"
        );

        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setPrefWidth(260);
        usernameField.setStyle(
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-padding: 10px;" +
                "-fx-font-size: 14px;"
        );

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setPrefWidth(260);
        passwordField.setStyle(
                "-fx-background-radius: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-padding: 10px;" +
                "-fx-font-size: 14px;"
        );

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(180);
        loginBtn.setStyle(
                "-fx-background-color: #2563eb;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 8px;" +
                "-fx-padding: 10 16 10 16;"
        );

        Label status = new Label();
        status.setStyle("-fx-text-fill: #0f172a; -fx-font-size: 13px;");

        Label hint = new Label(
                "Demo Users:\n" +
                "admin / 123\n" +
                "doc1 / 123\n" +
                "rec1 / 123\n" +
                "acc1 / 123"
        );
        hint.setStyle(
                "-fx-font-size: 12px;" +
                "-fx-text-fill: #64748b;" +
                "-fx-background-color: #e2e8f0;" +
                "-fx-padding: 10px;" +
                "-fx-background-radius: 8px;"
        );

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                UIHelper.showWarning("Validation Error", "Please enter both username and password.");
                return;
            }

            try {
                LoginService service = new LoginService();
                String role = service.login(username, password);

                if (role != null) {
                    status.setText("Login Success: " + role);

                    Session.setUser(username, role);
                    Dashboard.setRole(role);
                    new Dashboard().start(new Stage());
                    stage.close();

                } else {
                    status.setText("Invalid username or password.");
                    UIHelper.showError("Login Failed", "Invalid username or password.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                status.setText("Login failed due to system error.");
                UIHelper.showError("System Error", "Could not complete login.");
            }
        });

        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER_LEFT);
        card.setMaxWidth(340);
        card.setStyle(
                "-fx-background-color: white;" +
                "-fx-padding: 25px;" +
                "-fx-background-radius: 12px;" +
                "-fx-border-radius: 12px;" +
                "-fx-border-color: #cbd5e1;"
        );

        card.getChildren().addAll(
                title,
                subtitle,
                usernameLabel,
                usernameField,
                passwordLabel,
                passwordField,
                loginBtn,
                status,
                hint
        );

        VBox root = new VBox(card);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f8fafc;");

        Scene scene = new Scene(root, 520, 500);

        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}