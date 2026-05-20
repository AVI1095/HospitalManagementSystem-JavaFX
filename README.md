# Hospital Management System

## Overview
A **Hospital Management System** built using **JavaFX and MySQL**, designed to streamline and digitize hospital operations such as patient management, appointments, billing, prescriptions, and inventory tracking.  
The system includes **role-based authentication** and an **admin analytics dashboard** for efficient management and decision-making.

## Tech Stack
- Java (Core + OOP)
- JavaFX (User Interface)
- JDBC (Database Connectivity)
- MySQL (Database)
- Railway Cloud Database (Hosting)
- NetBeans IDE

## Features
-  Role-based Login System (Admin / Doctor / Staff)
-  Patient Management (Add / Update / View records)
-  Doctor Management Module
-  Appointment Booking System
-  Prescription Management
-  Billing and Invoice Generation
-  Inventory Management (Medicines & Supplies)
-  Admin Dashboard with Analytics
-  Audit Logs for tracking system activity

## Prerequisites
- Java JDK 8 or above
- NetBeans IDE
- MySQL Server
- Internet connection (for Railway Cloud Database)

## Setup Instructions

### 1. Clone Repository
```bash
git clone https://github.com/AVI1095/HospitalManagementSystem-JavaFX.git
```

### 2. Open Project
- Open **NetBeans IDE**
- Click on **File → Open Project**
- Select the cloned project folder

### 3. Configure Database
- Create a MySQL database (or use Railway Cloud DB)
- Import the `.sql` file (if provided)
- Open `DBConnection.java`
- Update the following:
  - Database URL
  - Username
  - Password

### 4. Run the Application
- Right-click the project in NetBeans
- Click **Run**

## Database Configuration
- Database is hosted on **Railway Cloud MySQL**
- Requires active internet connection
  
## 📂 Project Structure
```
src/
 ├── main/
 │    └── MainApp.java
 ├── model/
 │    ├── Appointment.java
 │    ├── AuditLog.java
 │    ├── Billing.java
 │    ├── Doctor.java
 │    ├── Expense.java
 │    ├── Medicine.java
 │    ├── Patient.java
 │    ├── PatientReportData.java
 │    └── Prescription.java
 ├── service/
 │    ├── AdminStatsService.java
 │    ├── AppointmentService.java
 │    ├── AuditLogService.java
 │    ├── BillingService.java
 │    ├── DoctorService.java
 │    ├── ExpenseService.java
 │    ├── LoginService.java
 │    ├── MedicineService.java
 │    ├── PatientReportService.java
 │    ├── PatientService.java
 │    └── PrescriptionService.java
 ├── DAO/
 │    ├── AppointmentDAO.java
 │    ├── AuditLogDAO.java
 │    ├── BillingDAO.java
 │    ├── DoctorDAO.java
 │    ├── ExpenseDAO.java
 │    ├── MedicineDAO.java
 │    ├── PatientDAO.java
 │    ├── PatientReportDAO.java
 │    └── PrescriptionDAO.java
 ├── ui/
 │    ├── AdminStatsUI.java
 │    ├── AppointmentUI.java
 │    ├── AuditLogUI.java
 │    ├── BillingUI.java
 │    ├── Dashboard.java
 │    ├── DoctorUI.java
 │    ├── ExpenseUI.java
 │    ├── InventoryUI.java
 │    ├── LoginUI.java
 │    ├── PatientReportUI.java
 │    ├── PatientUI.java
 │    ├── ResetPasswordUI.java
 │    └── UIHelper.java
 └── util/
      ├── CaptchaUtil.java
      ├── DBConnection.java
      └── Session.java
```

## Sample Login (Optional)
```
Admin:
Username: admin
Password: admin123
```

## Future Improvements
- AI-based patient analytics and predictions
- Web version using Spring Boot + React
- Improved role-based access control
- Full cloud deployment with REST APIs

## Important Notes
- Ensure MySQL server or Railway DB is running before starting the application
- Internet connection is required for cloud database access

## Author
**Aditya Vilas Ingle**  
GitHub: https://github.com/your-username  
LinkedIn: https://linkedin.com/in/your-profile  

## Project Type
This project was developed as an **academic + portfolio project** to demonstrate full-stack development using Java.
