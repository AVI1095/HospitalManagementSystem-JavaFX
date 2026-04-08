/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.ExpenseDAO;
import model.Expense;

import java.util.List;

public class ExpenseService {

    private ExpenseDAO dao = new ExpenseDAO();

    public void addExpense(String description, double amount, String date) {
        Expense e = new Expense(description, amount, date);
        dao.addExpense(e);

        new AuditLogService().logAction("Added expense: " + description + " (" + amount + ")");
    }

    public List<Expense> getExpenses() {
        return dao.getAllExpenses();
    }
}
