/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author adityaingle
 */
import DataAccessObject.AuditLogDAO;
import model.AuditLog;
import util.Session;

import java.util.List;

public class AuditLogService {

    private AuditLogDAO dao = new AuditLogDAO();

    public void logAction(String action) {
        AuditLog log = new AuditLog();
        log.setUsername(Session.getUsername());
        log.setRole(Session.getRole());
        log.setAction(action);
        dao.addLog(log);
    }

    public List<AuditLog> getLogs() {
        return dao.getAllLogs();
    }
}