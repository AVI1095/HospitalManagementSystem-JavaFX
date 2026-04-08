/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author adityaingle
 */
public class Appointment {

    private int id;
    private int patientId;
    private int doctorId;
    private String date;
    private String status;

    public Appointment() {}

    public Appointment(int patientId, int doctorId, String date, String status) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.status = status;
    }

    // GETTERS
    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getDoctorId() { return doctorId; }
    public String getDate() { return date; }
    public String getStatus() { return status; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }
    public void setDate(String date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
public String toString() {
    return "Appt#" + id + " | Patient:" + patientId + " | Doctor:" + doctorId + " | " + date;
}
}