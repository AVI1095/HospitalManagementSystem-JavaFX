/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;

/**
 *
 * @author adityaingle
 */
import model.Billing;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingDAO {

    public void addBill(Billing b) {
        String sql = "INSERT INTO billing " +
                "(patient_id, consultation_fee, test_fee, medicine_fee, total_amount, payment_method, date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, b.getPatientId());
            ps.setDouble(2, b.getConsultationFee());
            ps.setDouble(3, b.getTestFee());
            ps.setDouble(4, b.getMedicineFee());
            ps.setDouble(5, b.getTotalAmount());
            ps.setString(6, b.getPaymentMethod());
            ps.setString(7, b.getDate());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Billing> getAllBills() {
        List<Billing> list = new ArrayList<>();
        String sql = "SELECT * FROM billing";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Billing b = new Billing();
                b.setId(rs.getInt("id"));
                b.setPatientId(rs.getInt("patient_id"));
                b.setConsultationFee(rs.getDouble("consultation_fee"));
                b.setTestFee(rs.getDouble("test_fee"));
                b.setMedicineFee(rs.getDouble("medicine_fee"));
                b.setTotalAmount(rs.getDouble("total_amount"));
                b.setPaymentMethod(rs.getString("payment_method"));
                b.setDate(rs.getString("date"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}