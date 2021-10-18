package com;

import java.sql.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class AddBook {

    public void Menu() {
        System.out.println("[1] ADD BOOK DETAILS");
        System.out.println("[2] MODIFY BOOK DETAILS");
        System.out.println("[3] DELETE BOOK DETAILS");
        System.out.println("[4] LIST ALL BOOKS");
        System.out.println("[5] SEARCH BOOK DETAILS");
        System.out.println("[6] EXIT");
    }

    public void BOOKMENU() {
        try {
            int option;
            Scanner scn = new Scanner(System.in);
            do {

                Menu();
                System.out.print("Select option: ");
                option = scn.nextInt();
                switch (option) {
                    case 1:
                        ADDBOOK();
                        break;
                    case 2:
                        MODIFYBOOK();
                        break;
                    case 3:
                        DELETEBOOK();
                        break;
                    case 4:
                        SHOWALLBOOOKS();
                        break;
                    case 5:
                        SEARCHBOOK();
                        break;
                    case 6:
                        System.out.println("Exit...");
                        break;
                }
            } while (option != 6);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void SHOWALLBOOOKS() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + DataBases.TABLE_BOOK);
            int flag = 0;
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getInt(4));
                flag = 1;
            }
            if (flag == 0) {
                System.out.println("No Data is there ");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void ADDBOOK() {
        try {
            String name, author;
            int copies;
            Scanner sca = new Scanner(System.in);

            System.out.print("Enter Book Name: ");
            name = sca.nextLine();
            System.out.print("Enter Book Author Name: ");
            author = sca.nextLine();
            System.out.print("Enter Books Copies: ");
            copies = sca.nextInt();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = con.createStatement();
            boolean result = stmt.execute("INSERT INTO " + DataBases.TABLE_BOOK + "(BOOKNAME,AUTHOR,COPIES) VALUES ('"
                    + name + "','" + author + "'," + copies + ")");
            if (!result) {
                System.out.println("Record Inserted ");
            } else {
                System.out.println("Record Not Inserted");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public int GETIDOFBOOK() {

        int id = 0;
        String name;
        try {
            System.out.print("Enter Book name: ");
            Scanner sca = new Scanner(System.in);
            name = sca.nextLine();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT ID FROM " + DataBases.TABLE_BOOK + " WHERE BOOKNAME='" + name + "'");
            int counter = 0;
            while (rs.next()) {
                id = rs.getInt(1);
                counter = 1;
            }
            if (counter == 0) {
                return 0;
            } else {
                return id;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return 0;
    }

    public void MODIFYBOOK() {
        try {

            int id;
            id = GETIDOFBOOK();
            if (id == 0) {
                System.out.println("BOOK NOT FOUND");
            } else {

                String name, author;
                int copies;
                Scanner sca = new Scanner(System.in);

                System.out.println("Enter New Details");
                System.out.print("Enter Book Name: ");
                name = sca.nextLine();
                System.out.print("Enter Book Author Name: ");
                author = sca.nextLine();
                System.out.print("Enter Books Copies: ");
                copies = sca.nextInt();

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                        GetPassword.Password());
                Statement stmt = con.createStatement();
                boolean result = stmt.execute("UPDATE " + DataBases.TABLE_BOOK + " SET BOOKNAME='" + name
                        + "', AUTHOR='" + author + "',COPIES=" + copies + " WHERE ID=" + id + "");
                if (!result) {
                    System.out.println("Record Updated ");
                } else {
                    System.out.println("Record Not Updated");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void DELETEBOOK() {
        try {
            int id = GETIDOFBOOK();
            if (id == 0) {
                System.out.println("Book not found");
            } else {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                        GetPassword.Password());
                Statement stmt = con.createStatement();
                boolean result = stmt.execute("DELETE FROM " + DataBases.TABLE_BOOK + " WHERE ID=" + id + "");
                if (!result) {
                    System.out.println("Book Deleted");
                } else {
                    System.out.println("Book not Deleted");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void SEARCHBOOK() {
        String name;
        try {
            System.out.print("Enter Book name: ");
            Scanner sca = new Scanner(System.in);
            name = sca.nextLine();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT * FROM " + DataBases.TABLE_BOOK + " WHERE BOOKNAME LIKE '%" + name + "%'");
            int counter = 0;
            while (rs.next()) {
                System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
                counter = 1;
            }
            if (counter == 0) {
                System.out.println("Book not found");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
