/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.DoctorDAO;
import model.Doctor;
import java.util.List;

public class DoctorService {

    private DoctorDAO dao = new DoctorDAO();

    public void registerDoctor(String name, String spec, String contact) {
        Doctor d = new Doctor();
        d.setName(name);
        d.setSpecialization(spec);
        d.setContact(contact);

        dao.addDoctor(d);
        new AuditLogService().logAction("Added doctor: " + name);
    }

    public void addDoctor(String name, String spec, String contact) {
        Doctor d = new Doctor();
        d.setName(name);
        d.setSpecialization(spec);
        d.setContact(contact);

        dao.addDoctor(d);
        new AuditLogService().logAction("Added doctor: " + name);
    }

    public List<Doctor> getDoctors() {
        return dao.getAllDoctors();
    }

    public void deleteDoctor(int id) {
        dao.deleteDoctor(id);
        new AuditLogService().logAction("Deleted doctor ID: " + id);
    }
}