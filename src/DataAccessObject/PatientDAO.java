/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessObject;

/**
 *
 * @author adityaingle
 */

import model.Patient;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PatientDAO {
    

    public void addPatient(Patient patient) {
        try {
        Connection con = DBConnection.getConnection();

        String query = "INSERT INTO patients(name, age, gender, disease, contact) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, patient.getName());
        ps.setInt(2, patient.getAge());
        ps.setString(3, patient.getGender());
        ps.setString(4, patient.getDisease());
        ps.setString(5, patient.getContact());

        ps.executeUpdate();
        System.out.println("Patient added to database!");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // FETCH all patients from database
    public List<Patient> getAllPatients() {
        List<Patient> list = new ArrayList<>();

    try {
        Connection con = DBConnection.getConnection();
        String query = "SELECT * FROM patients";

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Patient p = new Patient(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("disease"),
                rs.getString("contact")
            );
            list.add(p);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
    }
    
    
    public void updatePatient(int id, String name, int age) {
    String query = "UPDATE patients SET name=?, age=? WHERE id=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setInt(3, id);
        
        

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Patient updated!");
        } else {
            System.out.println("Patient not found!");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    
    
    public void deletePatient(int id) {
    String query = "DELETE FROM patients WHERE id=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setInt(1, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println("Patient deleted!");
        } else {
            System.out.println("Patient not found!");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    
    
    public Patient getPatientById(int id) {
    String query = "SELECT * FROM patients WHERE id=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(query)) {

        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new Patient(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("disease"),
                rs.getString("contact")
            );
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
    
}
