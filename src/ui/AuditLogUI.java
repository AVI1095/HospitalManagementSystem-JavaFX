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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AuditLogUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = UIHelper.sectionTitle("Audit Logs");
        Label subtitle = new Label("Track who did what and when");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

        TableView<model.AuditLog> table = new TableView<>();
        table.setPrefHeight(500);
        table.setMaxWidth(Double.MAX_VALUE);

        TableColumn<model.AuditLog, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        idCol.setPrefWidth(70);

        TableColumn<model.AuditLog, String> userCol = new TableColumn<>("Username");
        userCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("username"));
        userCol.setPrefWidth(140);

        TableColumn<model.AuditLog, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("role"));
        roleCol.setPrefWidth(120);

        TableColumn<model.AuditLog, String> actionCol = new TableColumn<>("Action");
        actionCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("action"));
        actionCol.setPrefWidth(350);

        TableColumn<model.AuditLog, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("actionTime"));
        timeCol.setPrefWidth(220);

        table.getColumns().addAll(idCol, userCol, roleCol, actionCol, timeCol);

        service.AuditLogService als = new service.AuditLogService();
        table.setItems(FXCollections.observableArrayList(als.getLogs()));

        VBox layout = UIHelper.createRoot();
        layout.setFillWidth(true);
        layout.getChildren().addAll(
                title,
                subtitle,
                table
        );

        ScrollPane scrollPane = new ScrollPane(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 980, 650);

        stage.setTitle("Audit Logs");
        stage.setScene(scene);
        stage.show();
    }
}