package com;

import java.sql.*;
import java.util.Scanner;
import java.io.File;

public class ForgotPasseword {
    public boolean ForGot() {

        try {
            String email;
            Scanner sca = new Scanner(System.in);
            System.out.print("Enter Email Id: ");
            email = sca.nextLine();

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = con.createStatement();
            ResultSet rs = stmt
                    .executeQuery("SELECT * FROM " + DataBases.TABLE_LOGIN + "WHERE EMAILID='" + email + "'");
            int counter = 0;
            while (rs.next()) {
                counter++;
            }
            if (counter == 0) {
                System.out.println("Invalid Details Entered");
            } else {
                String password, cpassword;
                System.out.println("Enter New Password");
                password = sca.nextLine();
                System.out.println("Enter Confirm password");
                cpassword = sca.nextLine();

                if (password.compareTo(cpassword) != 0) {
                    System.out.println("Confirm Password not matched with password");
                } else {
                    boolean result = stmt
                            .execute("UPDATE " + DataBases.TABLE_LOGIN + " SET PASSWORD='" + password + "'");
                    if (!result) {
                        System.out.println("Password Updated");
                        return true;
                    } else {
                        System.out.println("Error Occured During ");
                        return false;
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
