package src.javaproject.classes;

import src.javaproject.exceptions.NegativeValueException;
import src.javaproject.exceptions.WrongDateException;

import java.time.LocalDate;

/**
 * Record class used to store Contract data for FreelanceWorker objects
 * @param startDate indicates the start date for the freelancer
 * @param endDate indicates the end date for the freelancer
 * @param salary
 */
public record Contract (LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) {
    public Contract(LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) {
        if (startDate().isBefore(LocalDate.now())) {
            throw new WrongDateException("Start date must be today or later!");
        } else if (endDate().isBefore(LocalDate.now()) || endDate().isEqual(LocalDate.now())) {
            throw new WrongDateException("End date must be at least tomorrow!");
        } else if(companyId <= 0) {
            throw new NegativeValueException("SQL indexing starts at 1, so the company id can never be smaller than that!");
        } else {
            this.startDate = startDate;
            this.endDate = endDate;
            this.salary = salary;
            this.companyId = companyId;
        }
    }
}
