/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;

/**
 *
 * @author adityaingle
 */
import model.Medicine;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    public void addMedicine(Medicine m) {
        String sql = "INSERT INTO medicine(name, quantity, expiry_date, threshold_qty) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getName());
            ps.setInt(2, m.getQuantity());
            ps.setString(3, m.getExpiryDate());
            ps.setInt(4, m.getThresholdQty());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicine";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Medicine m = new Medicine();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setQuantity(rs.getInt("quantity"));
                m.setExpiryDate(rs.getString("expiry_date"));
                m.setThresholdQty(rs.getInt("threshold_qty"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteMedicine(int id) {
        String sql = "DELETE FROM medicine WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Medicine getMedicineByName(String name) {
        String sql = "SELECT * FROM medicine WHERE LOWER(name)=LOWER(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name.trim());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Medicine m = new Medicine();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setQuantity(rs.getInt("quantity"));
                m.setExpiryDate(rs.getString("expiry_date"));
                m.setThresholdQty(rs.getInt("threshold_qty"));
                return m;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateQuantityByName(String name, int newQty) {
        String sql = "UPDATE medicine SET quantity=? WHERE LOWER(name)=LOWER(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newQty);
            ps.setString(2, name.trim());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Medicine> getLowStockMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicine WHERE quantity < threshold_qty";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Medicine m = new Medicine();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setQuantity(rs.getInt("quantity"));
                m.setExpiryDate(rs.getString("expiry_date"));
                m.setThresholdQty(rs.getInt("threshold_qty"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Medicine> getExpiringSoonMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicine WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL 30 DAY)";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Medicine m = new Medicine();
                m.setId(rs.getInt("id"));
                m.setName(rs.getString("name"));
                m.setQuantity(rs.getInt("quantity"));
                m.setExpiryDate(rs.getString("expiry_date"));
                m.setThresholdQty(rs.getInt("threshold_qty"));
                list.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}