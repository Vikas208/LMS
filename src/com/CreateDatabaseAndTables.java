package com;

import java.sql.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CreateDatabaseAndTables {

    CreateDatabaseAndTables() {
        CreateDataBase();
        CreateLoginTable();
        CreateBookTable();
        CreateBorrowedTable();

    }

    public void CreateDataBase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            boolean result = stmt.execute("CREATE DATABASE " + DataBases.DATABASE_NAME);
            if (!result) {
                System.out.println("DataBase Created");
            } else {
                System.out.println("Error Generated during Creation of DataBase");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void CreateTable(String query) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            boolean result = stmt.execute(query);
            if (!result) {
                System.out.println("Table Created");
            } else {
                System.out.println("Error Generated during Creation of Table");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void CreateBookTable() {
        String sql = "CREATE TABLE BOOKS (ID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,BOOKNAME VARCHAR(50) NOT NULL,AUTHOR VARCHAR(50) NOT NULL,COPIES INT(10) NOT NULL)";
        CreateTable(sql);
    }

    public void CreateLoginTable() {
        String sql = "CREATE TABLE LOGIN(USERNAME VARCHAR(100) NOT NULL,PASSWORD VARCHAR(100) NOT NULL,EMAILID VARCHAR(50))";
        CreateTable(sql);
    }

    public void CreateBorrowedTable() {
        String sql = "CREATE TABLE BORROWED(ID INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY,NAME VARCHAR(50) NOT NULL,PHONENUMBER VARCHAR(10) NOT NULL,BOOKNAME VARCHAR(50) NOT NULL,DATEOFBORROWED VARCHAR(15) NOT NULL,RETURNDATE VARCHAR(15) NOT NULL,HASRETURN VARCHAR(4))";
        CreateTable(sql);
    }
}