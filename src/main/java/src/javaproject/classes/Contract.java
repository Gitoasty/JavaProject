package src.javaproject.classes;

import src.javaproject.exceptions.NegativeValueException;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Record class used to store Contract data for FreelanceWorker objects
 * @param id contract id
 * @param startDate indicates the start date for the freelancer
 * @param endDate indicates the end date for the freelancer
 * @param companyId indicates company owning the contract
 * @param salary
 */
public record Contract (Integer id, LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) implements Serializable {

    public Contract(Integer id, LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) {
        if (endDate.isBefore(startDate)) {
            throw new NegativeValueException("End date must be after start date!");
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
