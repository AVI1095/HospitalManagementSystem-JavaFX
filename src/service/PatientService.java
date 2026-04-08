/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.PatientDAO;
import model.Patient;
import java.util.List;

public class PatientService {
    private PatientDAO dao = new PatientDAO();
    private PatientDAO patientDAO = new PatientDAO();

    public void registerPatient(String name, int age, String gender, String disease, String contact) {
        Patient p = new Patient(name, age, gender, disease, contact);
        dao.addPatient(p);
        new AuditLogService().logAction("Added patient: " + name);
    }

    public List<Patient> getPatients() {
        return dao.getAllPatients();
    }

    public void updatePatient(int id, String name, int age) {
        dao.updatePatient(id, name, age);
        new AuditLogService().logAction("Updated patient ID: " + id);
    }

    public void deletePatient(int id) {
        dao.deletePatient(id);
        new AuditLogService().logAction("Deleted patient ID: " + id);
    }

    public Patient getPatient(int id) {
        return dao.getPatientById(id);
    }

    public Patient getPatientById(int id) {
        return patientDAO.getPatientById(id);
    }
}