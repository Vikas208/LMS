package com;

import java.io.FileWriter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class App {
    public static void LoginMenu() {
        System.out.println("[1] Login");
        System.out.println("[2] ForGot Password");
        System.out.println("[3] Exit");
    }

    public static boolean IsDataBaseCreated() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select schema_name from information_schema.schemata where schema_name ='"
                    + DataBases.DATABASE_NAME + "';");
            int counter = 0;
            while (rs.next()) {
                counter++;
            }
            if (counter == 0) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return true;
    }

    public static int LoginSystem() {

        System.out.println("Login Page");
        Login l = new Login();
        int result = l.LoginInSystem();
        if (result == 1) {
            System.out.println("You Logined");
            return 1;
        } else {
            System.out.println("Username and Password Not matched");
            return 0;
        }

    }

    public static void Menu() {
        System.out.println("[1] BOOKS MENU");
        System.out.println("[2] BORROW BOOKS MENU");
        System.out.println("[3] EXIT");

    }

    public static void GenerateLogFile(int log) {
        try {

            FileWriter fp = new FileWriter("log.txt", true);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            if (log == 1) {
                fp.write("Logged in: " + dtf.format(now) + "\n");
            } else {
                fp.write("Logged out: " + dtf.format(now) + "\n");
            }
            fp.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Scanner sca = new Scanner(System.in);
            if (!IsDataBaseCreated()) {
                CreateDatabaseAndTables obj = new CreateDatabaseAndTables();
                do {
                    System.out.println("SIGNUP PAGE");
                    SignUp ob = new SignUp();
                    boolean result = ob.CreateAccount();
                    if (!result) {
                        System.out.println("Account Created");
                        break;
                    } else {
                        System.err.println("Error Occured During Creation of Account");
                    }
                } while (true);
            }

            int choice;
            int flag = 0;
            do {
                LoginMenu();
                System.out.println("Select Option: ");
                choice = sca.nextInt();
                switch (choice) {
                    case 1:
                        flag = LoginSystem();
                        GenerateLogFile(1);
                        break;
                    case 2:
                        ForgotPasseword f = new ForgotPasseword();
                        boolean r = f.ForGot();
                        if (r) {
                            flag = LoginSystem();
                            GenerateLogFile(1);
                            break;
                        }
                        break;
                    case 3:
                        System.out.println("Exit...");
                        GenerateLogFile(0);
                        flag = 2;
                        break;
                    default:
                        System.out.println("You Select Wrong Option");
                        break;
                }
            } while (flag != 1 && flag != 2);
            if (flag == 1) {
                do {
                    Menu();
                    System.out.print("Select Option: ");
                    choice = sca.nextInt();
                    switch (choice) {
                        case 1:
                            AddBook a = new AddBook();
                            a.BOOKMENU();
                            break;
                        case 2:
                            BoorowBooks b = new BoorowBooks();
                            b.BORROWBOOKSMENU();
                            break;
                        case 3:
                            System.out.println("Exit...");
                            GenerateLogFile(0);
                            break;
                    }

                } while (choice != 3);
            }
            sca.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
