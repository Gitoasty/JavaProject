package src.javaproject.classes;

import src.javaproject.exceptions.NegativeValueException;

import java.time.LocalDate;

/**
 * Record class used to store Contract data for FreelanceWorker objects
 * @param id contract id
 * @param startDate indicates the start date for the freelancer
 * @param endDate indicates the end date for the freelancer
 * @param companyId indicates company owning the contract
 * @param salary
 */
public record Contract (Integer id, LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) {
    public Contract(Integer id, LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) {
        if (startDate().isBefore(LocalDate.now())) {
            throw new NegativeValueException("Start date must be today or later!");
        } else if (endDate().isBefore(LocalDate.now()) || endDate().isEqual(LocalDate.now())) {
            throw new NegativeValueException("End date must be at least tomorrow!");
        } else if(companyId <= 0) {
            throw new NegativeValueException("SQL indexing starts at 1, so the company id can never be smaller than that!");
        } else {
            this.id = id;
            this.startDate = startDate;
            this.endDate = endDate;
            this.salary = salary;
            this.companyId = companyId;
        }
    }
}
