package src.javaproject.interfaces;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Interface for handling logging into the app and creation of new accounts
 */
public interface LoginManagement {

    /**
     * Checks whether user exists and login password is correct
     * @param credentials array of Strings containing valid user tag, hashed password and 'admin' if user is an admin
     * @param user user tag linked to specific user
     * @param pass password to be tested
     * @return true if login data provided is valid
     */
    private static boolean loginLogic(String[] credentials, String user, String pass) {
        boolean match = false;

        BCrypt.Result result = BCrypt.verifyer().verify(pass.toCharArray(), credentials[1]);

        if (credentials[0].equals(user) && result.verified) {
            match = true;
        }

        return match;
    }

    /**
     * Logs the user in if user exists and password is correct
     * @param user user tag used for logging in
     * @param pass user password to be tested
     * @return Login result, 0 if unsuccessful, 1 if admin, 2 if user
     */
    public static int attemptLogin(String user, String pass) {
        Logger logger = LoggerFactory.getLogger(LoginManagement.class);

        if (user.isEmpty() || pass.isEmpty()) {
            logger.info("Missing username or password");
            return 0;
        }

        File file = new File("src/main/resources/login_info.txt");
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bf.readLine()) != null) {
                String[] entry = line.split(" ");
                if (entry.length > 1 && loginLogic(entry, user, pass)) {
                    return entry.length == 3 ? 1 : 2;
                }
            }
        } catch (IOException e) {
            logger.error("Problem reading stored credentials");
        }
        return 0;
    }

}
