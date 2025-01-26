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
        if (!user.isEmpty() && !pass.isEmpty()) {
            try {
                File f = new File("src/main/resources/login_info.txt");
                FileReader fr = new FileReader(f);
                BufferedReader bf = new BufferedReader(fr);

                ArrayList<String> lines = new ArrayList<>();

                int counter = 0;
                while (lines.add(bf.readLine()) && lines.get(counter) != null) {
                    String[] temp = lines.get(counter++).split(" ");

                    if (temp.length > 1) {
                        if (loginLogic(temp, user, pass)) {
                            if (temp.length == 3) {
                                System.out.println("Logged in as admin");
                            } else if (temp.length == 2) {
                                System.out.println("Logged in");
                            }
                            break;
                        }
                    }
                }
                System.out.println("aaaa");
            } catch (IOException | NullPointerException e) {
                System.out.println("nope");
            }
        }
    }
}
