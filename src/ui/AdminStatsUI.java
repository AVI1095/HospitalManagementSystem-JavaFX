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
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Map;

public class AdminStatsUI extends Application {

    @Override
    public void start(Stage stage) {

        service.AdminStatsService stats = new service.AdminStatsService();

        Label title = UIHelper.sectionTitle("Admin Stats & Analytics");
        Label subtitle = new Label("Overview of hospital performance and trends");
        subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #475569;");

        Label patientsToday = new Label("Total Patients Today: " + stats.getTotalPatientsToday());
        patientsToday.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label appointmentsToday = new Label("Total Appointments Today: " + stats.getTotalAppointmentsToday());
        appointmentsToday.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        Label totalRevenue = new Label("Total Revenue: ₹" + stats.getTotalRevenue());
        totalRevenue.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #1e293b;");

        // Revenue chart
        CategoryAxis x1 = new CategoryAxis();
        x1.setLabel("Date");
        NumberAxis y1 = new NumberAxis();
        y1.setLabel("Revenue");
        BarChart<String, Number> revenueChart = new BarChart<>(x1, y1);
        revenueChart.setTitle("Revenue by Date");
        revenueChart.setPrefHeight(320);

        XYChart.Series<String, Number> revenueSeries = new XYChart.Series<>();
        revenueSeries.setName("Revenue");

        for (Map.Entry<String, Double> e : stats.getRevenueByDate().entrySet()) {
            revenueSeries.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
        }
        revenueChart.getData().add(revenueSeries);

        // Disease trends chart
        PieChart diseaseChart = new PieChart();
        diseaseChart.setTitle("Disease Trends");
        diseaseChart.setPrefHeight(320);

        for (Map.Entry<String, Integer> e : stats.getDiseaseTrends().entrySet()) {
            diseaseChart.getData().add(new PieChart.Data(e.getKey(), e.getValue()));
        }

        // Doctor performance chart
        CategoryAxis x2 = new CategoryAxis();
        x2.setLabel("Doctor");
        NumberAxis y2 = new NumberAxis();
        y2.setLabel("Appointments");
        BarChart<String, Number> doctorChart = new BarChart<>(x2, y2);
        doctorChart.setTitle("Doctor Performance");
        doctorChart.setPrefHeight(320);

        XYChart.Series<String, Number> doctorSeries = new XYChart.Series<>();
        doctorSeries.setName("Patients Handled");

        for (Map.Entry<String, Integer> e : stats.getDoctorPerformance().entrySet()) {
            doctorSeries.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
        }
        doctorChart.getData().add(doctorSeries);

        VBox content = UIHelper.createRoot();
        content.setFillWidth(true);
        content.getChildren().addAll(
                title,
                subtitle,
                patientsToday,
                appointmentsToday,
                totalRevenue,
                revenueChart,
                diseaseChart,
                doctorChart
        );

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        Scene scene = new Scene(scrollPane, 1000, 780);

        stage.setTitle("Admin Stats & Analytics");
        stage.setScene(scene);
        stage.show();
    }
}