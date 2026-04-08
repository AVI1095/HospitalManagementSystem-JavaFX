/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author adityaingle
 */
public class Doctor {

    private int id;
    private String name;
    private String specialization;
    private String contact;

    // ✅ Default constructor
    public Doctor() {}

    // ✅ Parameterized constructor
    public Doctor(int id, String name, String specialization, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    // ✅ GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getContact() {
        return contact;
    }

    // ✅ SETTERS
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @Override
public String toString() {
    return id + " - " + name;
}
}
