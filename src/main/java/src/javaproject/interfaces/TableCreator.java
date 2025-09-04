package src.javaproject.interfaces;

import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Interface for storing database related methods
 */
public interface TableCreator {

    /**
     * Creates table for storing user account data
     * @param logger logger of controller calling this method
     */
    static void accountData(Logger logger) {
        try (   Connection conn = DatabaseUtilities.getConnection(logger);
                Statement stat = conn.createStatement()) {

            String creationString = "CREATE TABLE IF NOT EXISTS " + "accounts" + "("
                    +"userTag TEXT PRIMARY KEY NOT NULL, "
                    +"password TEXT NOT NULL, "
                    +"role TEXT NOT NULL"
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.error("Problem creating account table");
        }
    }

    /**
     * Creates table for storing worker data
     * @param logger logger of controller calling this method
     */
    static void workerData(Logger logger) {
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             Statement stat = conn.createStatement()) {

            String creationString = "CREATE TABLE IF NOT EXISTS " + "workers" + "("
                    +"id TEXT PRIMARY KEY NOT NULL, "
                    +"fName TEXT NOT NULL, "
                    +"lName TEXT NOT NULL, "
                    +"type TEXT, " //Gets assigned by admin, FULL-TIME for staying workers, contract id for Freelancers
                    +"experience INTEGER"
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.error("Problem creating worker table");
        }
    }

    /**
     * Creates table for storing project data
     * @param logger logger of controller calling this method
     */
    static void projectData(Logger logger) {
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             Statement stat = conn.createStatement()) {

            String creationString = "CREATE TABLE IF NOT EXISTS " + "projects" + "("
                    +"id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"name TEXT NOT NULL, "
                    +"estimatedTime INTEGER, " //time in days
                    +"tasks TEXT, " //tasks separated by commas
                    +"workers TEXT" //user tags separated by commas
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.error("Problem creating project table");
        }
    }

    /**
     * Creates table for storing company data
     * @param logger logger of controller calling this method
     */
    static void companyData(Logger logger) {
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             Statement stat = conn.createStatement()) {

            String creationString = "CREATE TABLE IF NOT EXISTS " + "companies" + "("
                    +"id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"name TEXT NOT NULL, "
                    +"workers TEXT" //user tags separated by commas
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.error("Problem creating company table");
        }
    }

    /**
     * Creates table for storing contract data
     * @param logger logger of controller calling this method
     */
    static void contractData(Logger logger) {
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             Statement stat = conn.createStatement()) {

            String creationString = "CREATE TABLE IF NOT EXISTS " + "contracts" + "("
                    +"id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"start DATE NOT NULL, "
                    +"end DATE NOT NULL, "
                    +"salary INTEGER NOT NULL, "
                    +"companyId INTEGER NOT NULL"
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.error("Problem creating contract table");
        }
    }

    static void projectsForWorker(Logger logger) {
        try (Connection conn = DatabaseUtilities.getConnection(logger);
             Statement stat = conn.createStatement()) {

            String creationString = "CREATE TABLE IF NOT EXISTS " + "workerProjects" + "("
                    +"id TEXT PRIMARY KEY NOT NULL, "
                    +"projects TEXT NOT NULL" //stores projects that the worker has completed, separated by " " "
                    +")";
            stat.executeUpdate(creationString);
        } catch (SQLException _) {
            logger.error("Problem creating contract table");
        }
    }
}