/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.MedicineDAO;
import model.Medicine;

import java.util.List;

public class MedicineService {

    private MedicineDAO dao = new MedicineDAO();

    public void addMedicine(String name, int quantity, String expiryDate, int thresholdQty) {
        Medicine m = new Medicine(name, quantity, expiryDate, thresholdQty);
        dao.addMedicine(m);

        new AuditLogService().logAction("Added medicine: " + name);
    }

    public List<Medicine> getMedicines() {
        return dao.getAllMedicines();
    }

    public void deleteMedicine(int id) {
        dao.deleteMedicine(id);
        new AuditLogService().logAction("Deleted medicine ID: " + id);
    }

    public Medicine getMedicineByName(String name) {
        return dao.getMedicineByName(name);
    }

    public void reduceMedicineStock(String medicineName, int amount) {
        Medicine m = dao.getMedicineByName(medicineName);

        if (m != null) {
            int newQty = m.getQuantity() - amount;
            if (newQty < 0) {
                newQty = 0;
            }
            dao.updateQuantityByName(medicineName, newQty);
        }
    }

    public void reduceMultipleMedicines(String medicinesText) {
        if (medicinesText == null || medicinesText.trim().isEmpty()) {
            return;
        }

        String[] meds = medicinesText.split(",");

        for (String med : meds) {
            String cleanName = med.trim();
            if (!cleanName.isEmpty()) {
                reduceMedicineStock(cleanName, 1);
            }
        }
    }

    public List<Medicine> getLowStockMedicines() {
        return dao.getLowStockMedicines();
    }

    public List<Medicine> getExpiringSoonMedicines() {
        return dao.getExpiringSoonMedicines();
    }
}