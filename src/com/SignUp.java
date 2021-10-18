package com;

import java.sql.*;
import java.util.*;
import java.io.File;

public class SignUp {

    public boolean CreateAccount() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = conn.createStatement();
            String username, password, mailid, confirmpassword;

            Scanner sca = new Scanner(System.in);
            do {
                System.out.println("Enter Username : ");
                username = sca.nextLine();
                System.out.println("Enter Password: ");
                password = sca.nextLine();
                System.out.println("Enter Confirm Password: ");
                confirmpassword = sca.nextLine();
                System.out.println("Enter Mail id: ");
                mailid = sca.nextLine();

                if (password.compareTo(confirmpassword) == 1) {
                    System.out.println("Confirm Password not matched");

                } else {
                    break;
                }
            } while (true);
            // sca.close();
            boolean result = stmt.execute("INSERT INTO  " + DataBases.TABLE_LOGIN
                    + "(USERNAME,PASSWORD,EMAILID) VALUES('" + username + "','" + password + "','" + mailid + "')");

            return result;

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return true;
    }
}
