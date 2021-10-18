package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Statement;

public class BoorowBooks {
    public void Menu() {
        System.out.println("[1] Allocate Books");
        System.out.println("[2] Take Books");
        System.out.println("[3] Show All Allocated Books");
        System.out.println("[4] Search Student");
        System.out.println("[5] Modify Allocate Books Details");
        System.out.println("[6] Exit");
    }

    public void BORROWBOOKSMENU() {
        try {
            int choice;
            do {
                Menu();
                Scanner sca = new Scanner(System.in);
                System.out.print("Select option: ");
                choice = sca.nextInt();
                switch (choice) {
                    case 1:
                        ALLOCATEBOOK();
                        break;
                    case 2:
                        TAKEBOOKBACK();
                        break;
                    case 3:
                        SHOWDETAILS();
                        break;
                    case 4:
                        SEARCHSTUDENT();
                        break;
                    case 5:
                        MODIFYDETAILS();
                        break;
                    case 6:
                        System.out.println("Exit...");
                        break;
                }
            } while (choice != 6);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean checkBookDetails(String bookname, String Authorname) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            String Query = "select * from " + DataBases.TABLE_BOOK + " where BOOKNAME='" + bookname + "' AND AUTHOR='"
                    + Authorname + "'";
            ResultSet rs = stmt.executeQuery(Query);
            int counter = 0;
            int stock = 0;
            while (rs.next()) {
                counter++;
                stock = rs.getInt(4);
            }
            if (counter == 0 || stock == 0) {
                System.out.println("Book not Available");
                return false;
            } else {
                stock = stock - 1;
                boolean result = stmt.execute("update " + DataBases.TABLE_BOOK + "set copies=" + stock
                        + " where bookname='" + bookname + "' AND author='" + Authorname + "'");
                if (!result) {
                    System.out.println("Copies Updated");
                    return true;
                } else {
                    System.out.println("Error Occured During Update of Book copies");
                    return false;
                }

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return false;
    }

    public void ALLOCATEBOOK() {
        try {

            String name, phoneno, bookname, dateofborrow, returnbookdate, authorname;
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            Scanner sca = new Scanner(System.in);
            System.out.print("Enter Student Name: ");
            name = sca.nextLine();
            System.out.print("Enter Phone number: ");
            phoneno = sca.nextLine();
            System.out.print("Enter Book name Which Borrwed: ");
            bookname = sca.nextLine();
            System.out.print("Enter Book author name: ");
            authorname = sca.nextLine();
            System.out.println();
            if (checkBookDetails(bookname, authorname)) {

                System.out.println("Enter Date Of Borrowed Book: ");
                dateofborrow = sca.nextLine();
                System.out.println("Enter Return Date: ");
                returnbookdate = sca.nextLine();
                bookname = bookname.concat(" " + authorname);
                boolean rs = stmt.execute("insert into " + DataBases.TABLE_BORROWED
                        + "(name,phonenumber,Bookname,dateofborrowed,returndate,hasreturn) values ('" + name + "','"
                        + phoneno + "','" + bookname + "','" + dateofborrow + "','" + returnbookdate + "','no')");

                if (!rs) {
                    System.out.println("Record Inserted");

                } else {
                    System.out.println("Error Occured During Inserting Recorded");
                }
            }
            conn.close();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean FindStudent(String name, String bookname) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + DataBases.TABLE_BORROWED + " WHERE NAME='" + name
                    + "' AND BOOKNAME like'%" + bookname + "%'");
            int counter = 0;
            while (rs.next()) {
                counter = 1;
                System.out.println("[" + rs.getInt(1) + "] " + rs.getString(2));
            }
            if (counter == 0) {
                System.out.println("Data Not Found");
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public void MODIFYBOOKSDATA(String bookname, String author) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + DataBases.TABLE_BOOK + " where bookname like'"
                    + bookname + "%' AND author like '%" + author + "'");
            int stock = 0;
            int counter = 0;
            while (rs.next()) {
                System.out.println("inside rs");
                System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4));
                counter++;
                stock = rs.getInt(4) + 1;
            }

            if (counter != 0) {
                boolean result = stmt.execute("update " + DataBases.TABLE_BOOK + " set copies=" + stock
                        + " where bookname like'" + bookname + "%' AND author like '%" + author + "'");

                if (!result) {
                    System.out.println("Record Updated");
                } else {
                    System.out.println("Record not updated");
                }
            }
            // conn.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void TAKEBOOKBACK() {
        try {
            String name, bookname, author;
            Scanner sca = new Scanner(System.in);
            System.out.println("Enter Student Name Please");
            name = sca.nextLine();
            System.out.println("Enter Book name: ");
            bookname = sca.nextLine();
            System.out.println("Enter Book author name: ");
            author = sca.nextLine();
            boolean result = FindStudent(name, bookname);
            if (result) {
                int option;
                System.out.print("Select your option: ");
                option = sca.nextInt();
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                        GetPassword.Password());
                Statement stmt = conn.createStatement();
                boolean re = stmt
                        .execute("Update " + DataBases.TABLE_BORROWED + " set hasreturn='yes' where id=" + option + "");
                if (!re) {
                    System.out.println("Record Updated");
                    MODIFYBOOKSDATA(bookname, author);

                } else {
                    System.out.println("Wrong option you choosed");
                }
                conn.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void SHOWDETAILS() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + DataBases.TABLE_BORROWED);
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4)
                                + " | " + rs.getString(5) + " | " + rs.getString(6) + " | " + rs.getString(7) + " | ");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void SEARCHSTUDENT() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            String name;
            System.out.println("Enter Name: ");
            Scanner sca = new Scanner(System.in);
            name = sca.nextLine();
            ResultSet rs = stmt
                    .executeQuery("select * from " + DataBases.TABLE_BORROWED + "where name like '%" + name + "%'");
            int counter = 0;
            while (rs.next()) {
                System.out.println(
                        rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4)
                                + " | " + rs.getString(5) + " | " + rs.getString(6) + " | " + rs.getString(7) + " | ");
                counter = 1;
            }
            if (counter == 0) {
                System.out.println("Details not found");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void MODIFYDETAILS() {
        try {
            String name, bookname;
            String phoneno, dateofborrow, returnbookdate, authorname, hasreturn;
            Scanner sca = new Scanner(System.in);
            System.out.println("Enter Student Name Please");
            name = sca.nextLine();
            System.out.println("Enter Book name: ");
            bookname = sca.nextLine();
            System.out.println("Enter Book author name: ");
            authorname = sca.nextLine();
            boolean result = FindStudent(name, bookname);
            if (result) {

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                        GetPassword.Password());
                Statement stmt = conn.createStatement();
                int option;
                System.out.print("Select your option: ");
                option = Integer.parseInt(sca.nextLine());
                System.out.print("Enter Student Name: ");
                name = sca.nextLine();
                System.out.print("Enter Phone number: ");
                phoneno = sca.nextLine();
                System.out.println("Enter Book name Which Borrwed: ");
                bookname = sca.nextLine();
                System.out.println("Enter Book author name: ");
                authorname = sca.nextLine();
                System.out.println("Has return book: ");
                hasreturn = sca.nextLine();
                if (checkBookDetails(bookname, authorname)) {
                    System.out.println("Enter Date Of Borrowed Book: ");
                    dateofborrow = sca.nextLine();
                    System.out.println("Enter Return Date: ");
                    returnbookdate = sca.nextLine();
                    bookname.concat(" " + authorname);
                    boolean rs = stmt.execute("Update " + DataBases.TABLE_BORROWED + "set name='" + name
                            + "', phonenumber='" + phoneno + "', Bookname='" + bookname + "',dateofborrowed='"
                            + dateofborrow + "',returndate='" + returnbookdate + "',hasreturn='" + hasreturn
                            + "' where id=" + option + "");

                    if (!rs) {
                        MODIFYBOOKSDATA(bookname, authorname);
                        System.out.println("Record Inserted");

                    } else {
                        System.out.println("Error Occured During Inserting Recorded");
                    }
                }

            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}