/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.PrescriptionDAO;
import model.Prescription;

import java.util.List;

public class PrescriptionService {

    private PrescriptionDAO dao = new PrescriptionDAO();

    public void savePrescription(int appointmentId, int patientId, int doctorId,
                                 String medicines, String tests, String remarks, String followUpDate) {
        Prescription p = new Prescription(
                appointmentId, patientId, doctorId,
                medicines, tests, remarks, followUpDate
        );

        dao.addPrescription(p);

        MedicineService ms = new MedicineService();
        ms.reduceMultipleMedicines(medicines);

        new AuditLogService().logAction("Saved prescription for patient ID " + patientId);
    }

    public List<Prescription> getAllPrescriptions() {
        return dao.getAllPrescriptions();
    }

    public List<Prescription> getPrescriptionsByDoctor(int doctorId) {
        return dao.getPrescriptionsByDoctor(doctorId);
    }

    public Prescription getPrescriptionById(int id) {
        return dao.getPrescriptionById(id);
    }
}