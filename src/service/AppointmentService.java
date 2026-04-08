/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.AppointmentDAO;
import model.Appointment;
import java.util.List;

public class AppointmentService {

    private AppointmentDAO dao = new AppointmentDAO();

    public void bookAppointment(int patientId, int doctorId, String date) {
        Appointment a = new Appointment(patientId, doctorId, date, "Scheduled");
        dao.addAppointment(a);

        new AuditLogService().logAction(
                "Created appointment for patient ID " + patientId + " with doctor ID " + doctorId
        );
    }

    public List<Appointment> getAppointments() {
        return dao.getAppointments();
    }

    public List<Appointment> getAppointmentsByDoctor(int doctorId) {
        return dao.getAppointmentsByDoctor(doctorId);
    }
}