package src.javaproject.classes;

import src.javaproject.interfaces.SerializeMarker;

import java.io.Serializable;
import java.util.Set;

/**
 * Base class for Project classes
 */
public abstract non-sealed class Project implements Serializable, SerializeMarker {
    protected Integer id;
    protected String name;
    protected Integer estimatedTime;
    protected Set<String> tasks;

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

    @Override
    public String toString() {
        return STR."\{id} - \{name} - \{estimatedTime} - \{tasks}";
    }
}
