/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author adityaingle
 */
public class Patient {
    private int id;
    private String name;
    private int age;
    private String gender;
    private String disease;
    private String contact;

    public Patient(String name, int age, String gender, String disease, String contact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.contact = contact;
    }
    
    public Patient(int id, String name, int age, String gender, String disease, String contact) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.disease = disease;
        this.contact = contact;
    }

//    public void display() {
//        System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getDisease() { return disease; }
    public String getContact() { return contact; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; } 
    public void setGender(String gender) { this.gender = gender; }
    public void setDisease(String disease) { this.disease = disease; }
    public void setContact(String contact) { this.contact = contact; }
    
@Override
public String toString() {
    return id + " - " + name;
}
}
