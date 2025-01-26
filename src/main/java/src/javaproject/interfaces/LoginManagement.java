package src.javaproject.interfaces;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.io.*;
import java.util.ArrayList;

/**
 * Interface for handling logging into the app and creation of new accounts
 */
public interface LoginManagement {

    private static boolean loginLogic(String[] credentials, String user, String pass) {
        boolean match = false;

        BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), credentials[1]);

        if (credentials[0].equals(user) && result.verified) {
            match = true;
        }

        return match;
    }

    public static void attemptLogin(String user, String pass) {
        if (user.isEmpty() || pass.isEmpty()) {
            return;
        }

        File file = new File("src/main/resources/login_info.txt");
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length > 1 && loginLogic(parts, user, pass)) {
                    System.out.println(parts.length == 3 ? "Logged in as admin" : "Logged in");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("nope");
        }
    }

}
