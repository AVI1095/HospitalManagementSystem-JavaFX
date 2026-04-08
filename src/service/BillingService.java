/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.BillingDAO;
import model.Billing;
import model.Prescription;
import java.util.List;

public class BillingService {

    private BillingDAO dao = new BillingDAO();

    public void generateBillFromPrescription(int prescriptionId,
                                             double consultationFee,
                                             double testFee,
                                             double medicineFee,
                                             String paymentMethod,
                                             String date) {

        PrescriptionService ps = new PrescriptionService();
        Prescription p = ps.getPrescriptionById(prescriptionId);

        if (p == null) {
            throw new RuntimeException("Prescription not found!");
        }

        double totalAmount = consultationFee + testFee + medicineFee;

        Billing b = new Billing(
                p.getPatientId(),
                consultationFee,
                testFee,
                medicineFee,
                totalAmount,
                paymentMethod,
                date
        );

        dao.addBill(b);

        new AuditLogService().logAction("Generated bill for patient ID " + p.getPatientId());
    }

    public List<Billing> getBills() {
        return dao.getAllBills();
    }
}