/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import util.DBConnection;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminStatsService {

    public int getTotalPatientsToday() {
        String sql = "SELECT COUNT(*) FROM patients WHERE DATE(created_at)=CURDATE()";
        return getSingleInt(sql);
    }

    public int getTotalAppointmentsToday() {
        String sql = "SELECT COUNT(*) FROM appointments WHERE date = CURDATE()";
        return getSingleInt(sql);
    }

    public double getTotalRevenue() {
        String sql = "SELECT COALESCE(SUM(total_amount),0) FROM billing";
        return getSingleDouble(sql);
    }

    public Map<String, Double> getRevenueByDate() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT date, SUM(total_amount) AS total FROM billing GROUP BY date ORDER BY date";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                map.put(rs.getString("date"), rs.getDouble("total"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public Map<String, Integer> getDiseaseTrends() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT disease, COUNT(*) AS count FROM patients GROUP BY disease ORDER BY count DESC";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                map.put(rs.getString("disease"), rs.getInt("count"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public Map<String, Integer> getDoctorPerformance() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT d.name, COUNT(a.id) AS count " +
                     "FROM doctors d LEFT JOIN appointments a ON d.id = a.doctor_id " +
                     "GROUP BY d.name ORDER BY count DESC";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                map.put(rs.getString("name"), rs.getInt("count"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    private int getSingleInt(String sql) {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private double getSingleDouble(String sql) {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) return rs.getDouble(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
