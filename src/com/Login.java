package com;

import java.sql.*;
import java.io.File;
import java.util.*;

public class Login {

    public int LoginInSystem() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/LMS", "root",
                    GetPassword.Password());
            Statement stmt = con.createStatement();

            Scanner scan = new Scanner(System.in);
            String username;
            String password;

            System.out.print("ENTER USER NAME: ");
            username = scan.nextLine();
            System.out.print("ENTER PASSWORD: ");
            password = scan.nextLine();

            // scan.close();

            ResultSet result = stmt.executeQuery("SELECT * FROM " + DataBases.TABLE_LOGIN + " WHERE USERNAME='"
                    + username + "' AND PASSWORD='" + password + "'");

            int counter = 0;
            while (result.next()) {
                counter++;
            }
            if (counter == 0) {
                return 0;
            } else {
                return 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
}
