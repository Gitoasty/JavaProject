package src.javaproject.classes;

import lombok.Getter;
import lombok.Setter;
import src.javaproject.enums.WorkerTypes;
import src.javaproject.interfaces.SerializeMarker;

import java.io.Serializable;

/**
 * Base class for FreelanceWorker and StayingWorker classes
 */
@Getter
@Setter
public abstract sealed class Worker implements Serializable, SerializeMarker permits FreelanceWorker, StayingWorker {
    protected String id;
    protected String firstName;
    protected String lastName;

    private WorkerTypes type;
    protected Integer workExperience;

    /**
     * returns the id attribute
     * @return worker id
     */
    public String idGetter() {
        return id;
    }

    /**
     * returns the first name attribute
     * @return first name of the worker
     */
    public String firstNameGetter() {
        return firstName;
    }

    /**
     * returns the last name attribute
     * @return last name of the worker
     */
    public String lastNameGetter() {
        return lastName;
    }

    /**
     * returns the work experience of the worker (measured in completed projects)
     * @return number of projects the worker has completed
     */
    public Integer experienceGetter() {
        return workExperience;
    }


}
