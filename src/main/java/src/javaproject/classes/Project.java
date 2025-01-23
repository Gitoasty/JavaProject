package src.javaproject.classes;

import java.util.Set;

/**
 * Base class for Project classes
 */
public abstract class Project {
    protected Integer id;
    protected String name;
    protected Integer estimatedTime;
    protected Set<String> tasks;

    /**
     * takes Integer argument to be set as the project id
     * @param i provided project id
     */
    public void idSetter(Integer i) {
        this.id = i;
    }

    /**
     * takes String argument to be set as the project name
     * @param n provided project name
     */
    public void nameSetter(String n) {
        this.name = n;
    }

    /**
     * takes Integer argument to be set as the estimated project duration
     * @param t provided estimated project duration (in days)
     */
    public void timeSetter(Integer t) {
        this.estimatedTime = t;
    }

    /**
     * takes a Set of Strings to be set as the tasks to be completed
     * @param t provided Set of tasks
     */
    public void taskSetter(Set<String> t) {
        this.tasks = t;
    }

    /**
     * used to get project id attribute
     * @return the project id
     */
    public Integer idGetter() {
        return id;
    }

    /**
     * returns the project name attribute
     * @return the project name
     */
    public String nameGetter() {
        return name;
    }

    /**
     * returns the estimated time attribute
     * @return the estimated time to complete the project
     */
    public Integer timeGetter() {
        return estimatedTime;
    }

    /**
     * returns the project tasks attribute
     * @return the project tasks in the form of Strings
     */
    public Set<String> taskGetter() {
        return tasks;
    }
}
