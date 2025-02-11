package src.javaproject.interfaces;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.slf4j.Logger;

import java.io.*;
import javafx.scene.control.Label;
import java.util.ArrayList;

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
     * @param logger logger of controller calling this method
     * @param user user tag used for logging in
     * @param pass user password to be tested
     * @return Login result, 0 if unsuccessful, 1 if admin, 2 if user
     */
    static int attemptLogin(Logger logger, String user, String pass) {

        if (user.isEmpty() || pass.isEmpty()) {
            logger.info("Missing username or password");
            return 0;
        }

        File file = new File("src/main/resources/data/login_info.txt");
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

    /**
     * Verifies that the passed parameters are valid and stores them in the database if they are
     * @param credentials ArrayList of Strings containing register credentials
     * @param indicator Label which displays any errors with passed credentials
     * @return true if everything is good, false if something is wrong
     */
    static boolean attemptRegister(Logger logger, ArrayList<String> credentials, Label indicator) {

        if (credentials.stream().anyMatch(String::isEmpty)) {
            ScreenUtilities.labelUpdater(indicator, "All fields must be filled");
            logger.info("A field was empty, register failed");
            return false;
        } else if (credentials.stream().anyMatch(s -> s.contains(" "))) {
            ScreenUtilities.labelUpdater(indicator, "Spaces not allowed");
            logger.info("A field contained a space, register failed");
            return false;
        } else if (DatabaseUtilities.accountExists(logger, credentials.get(2))) {
            ScreenUtilities.labelUpdater(indicator, "Account exists");
            logger.info("Account already exists, register failed");
            return false;
        } else if (!credentials.get(3).equals(credentials.get(4))) {
            ScreenUtilities.labelUpdater(indicator, "Passwords must match");
            logger.info("Passwords do not match, register failed");
            return false;
        } else if (credentials.get(3).length() < 8) {
            ScreenUtilities.labelUpdater(indicator, "Password too short");
            logger.info("Password is too short, register failed");
            return false;
        } else if (!RowAdder.addAccount(logger, credentials)) {
            ScreenUtilities.labelUpdater(indicator, "Something broke");
            logger.info("Failed to add account to table");
            return false;
        }

        ScreenUtilities.labelUpdater(indicator, "Register successful");
        logger.info("Register successful");
        return true;
    }
}
