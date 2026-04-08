///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package util;
//
///**
// *
// * @author adityaingle
// */
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class DBConnection {
//
//    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
//    private static final String USER = "root";
//    private static final String PASSWORD = "NicolaAvi5"; // 🔥 CHANGE THIS
//
//    public static Connection getConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver"); // load driver
//            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Database Connected Successfully!");
//            return con;
//        } catch (Exception e) {
//            System.out.println("Database Connection Failed!");
//            e.printStackTrace();
//        }
//        return null;
//    }
//}

package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://root:pTOBOevFVhVSYWUjhDHiHTIdLhaKWFXv@maglev.proxy.rlwy.net:40708/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "pTOBOevFVhVSYWUjhDHiHTIdLhaKWFXv";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Cloud Database Connected Successfully!");
            return con;
        } catch (Exception e) {
            System.out.println("Cloud Database Connection Failed!");
            e.printStackTrace();
        }
        return null;
    }
}