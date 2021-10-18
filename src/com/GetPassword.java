package com;

import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class GetPassword {

    public static String Password() throws FileNotFoundException {
        File fileobj = new File("src/com/Password.txt");
        Scanner scan = new Scanner(fileobj);
        String DATABASEPASSWORD = scan.nextLine();
        scan.close();
        return DATABASEPASSWORD;
    }
}
