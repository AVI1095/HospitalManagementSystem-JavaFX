/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;

/**
 *
 * @author adityaingle
 */
import model.Prescription;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDAO {

    public void addPrescription(Prescription p) {
        String sql = "INSERT INTO prescriptions " +
                "(appointment_id, patient_id, doctor_id, medicines, tests, remarks, follow_up_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getAppointmentId());
            ps.setInt(2, p.getPatientId());
            ps.setInt(3, p.getDoctorId());
            ps.setString(4, p.getMedicines());
            ps.setString(5, p.getTests());
            ps.setString(6, p.getRemarks());
            ps.setString(7, p.getFollowUpDate());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Prescription> getAllPrescriptions() {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Prescription p = new Prescription();
                p.setId(rs.getInt("id"));
                p.setAppointmentId(rs.getInt("appointment_id"));
                p.setPatientId(rs.getInt("patient_id"));
                p.setDoctorId(rs.getInt("doctor_id"));
                p.setMedicines(rs.getString("medicines"));
                p.setTests(rs.getString("tests"));
                p.setRemarks(rs.getString("remarks"));
                p.setFollowUpDate(rs.getString("follow_up_date"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Prescription> getPrescriptionsByDoctor(int doctorId) {
        List<Prescription> list = new ArrayList<>();
        String sql = "SELECT * FROM prescriptions WHERE doctor_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prescription p = new Prescription();
                p.setId(rs.getInt("id"));
                p.setAppointmentId(rs.getInt("appointment_id"));
                p.setPatientId(rs.getInt("patient_id"));
                p.setDoctorId(rs.getInt("doctor_id"));
                p.setMedicines(rs.getString("medicines"));
                p.setTests(rs.getString("tests"));
                p.setRemarks(rs.getString("remarks"));
                p.setFollowUpDate(rs.getString("follow_up_date"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 NEW: fetch one prescription by id
    public Prescription getPrescriptionById(int id) {
        String sql = "SELECT * FROM prescriptions WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Prescription p = new Prescription();
                p.setId(rs.getInt("id"));
                p.setAppointmentId(rs.getInt("appointment_id"));
                p.setPatientId(rs.getInt("patient_id"));
                p.setDoctorId(rs.getInt("doctor_id"));
                p.setMedicines(rs.getString("medicines"));
                p.setTests(rs.getString("tests"));
                p.setRemarks(rs.getString("remarks"));
                p.setFollowUpDate(rs.getString("follow_up_date"));
                return p;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}