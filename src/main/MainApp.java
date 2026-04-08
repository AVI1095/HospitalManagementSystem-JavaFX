/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author adityaingle
 */
import model.Patient;
import service.PatientService;
import java.util.List;
import util.DBConnection;
import java.sql.Connection;
public class MainApp {
     public static void main(String[] args) {
         Connection con = DBConnection.getConnection();
//        System.out.println("Hospital Management System Started");
        PatientService service = new PatientService();

        service.registerPatient("Aditya", 20, "Male", "Fever", "9876543210");
        service.registerPatient("sujal", 25, "Male", "Cold", "9123456780");


        List<Patient> patients = service.getPatients();

        for (Patient p : patients) {
            System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Age: " + p.getAge()+ ", Gender: " + p.getGender() + ", Disease: " + p.getDisease() + ", Contact: " + p.getContact());
        }
        
service.updatePatient(1, "Sujal", 21);

Patient p = service.getPatientById(1);

if (p != null) {
    System.out.println("Search Result: " + p.getName());
} else {
    System.out.println("Patient not found!");
}

service.deletePatient(2);

     }          
}
