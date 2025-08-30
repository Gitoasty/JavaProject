package src.javaproject.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.*;

/**
 * This class is used for storing assigned projects
 */
@Getter
@Setter
public class ProjectAssignment extends Project {
    private ArrayList<String> workers = new ArrayList<>();

    public ProjectAssignment(ProjectDraft pd) {
        this.id = pd.id;
        this.name = pd.name;
        this.estimatedTime = pd.estimatedTime;
        this.tasks = pd.tasks;
    }

    /**
     * constructs the ProjectAssignment object
     * @param pa builder object used to construct the ProjectAssignment
     */
    private ProjectAssignment(Builder pa) {
        this.id = pa.id;
        this.name = pa.name;
        this.workers = pa.workers;
        this.tasks = pa.tasks;
        this.estimatedTime = pa.estimatedTime;
    }

    /**
     * ensures we go to IdSetter next, starting point of the Builder Pattern
     * @return Builder as an object of type IdSetter
     */
    public static ProjectAssignment.IdSetter builder() {
        return new Builder();
    }

    /**
     * First step of the ProjectAssignment Builder process
     */
    public interface IdSetter {
        /**
         * guides us to the next step of the Builder Pattern
         * @param id takes an Integer to be set as project id
         * @return Builder as an object of type NameSetter
         */
        NameSetter id(Integer id);
    }

    /**
     * Second step of the ProjectAssignment Builder process
     */
    public interface NameSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param name takes a String to be set as project name
         * @return Builder as an object of type WorkerSetter
         */
        WorkerSetter name(String name);
    }

    /**
     * Third step of the ProjectAssignment Builder process
     */
    public interface WorkerSetter {

        /**
         * guides us to the final step of the Builder Pattern
         * @param workers takes a List of Strings to be set as the project workers
         * @return Builder as an object of type OptionalSetter
         */
        OptionalSetter workers(List<String> workers);
    }

    /**
     * Optional part of the ProjectAssignment Builder process, also contains the build() method
     */
    public interface OptionalSetter {

        /**
         * guides us back to the optional step of the Builder pattern, so we can call the build() method
         * @param tasks takes a Set of Strings to be set as project tasks
         * @return Builder as an object of type OptionalSetter
         */
        OptionalSetter taskSetter(Set<String> tasks);

        /**
         * guides us back to the optional step of the Builder pattern, so we can call the build() method
         * @param estimatedTime takes an Integer to be set as estimated project duration (in days)
         * @return Builder as an object of type OptionalSetter
         */
        OptionalSetter estimatedTimeSetter(Integer estimatedTime);

        /**
         * constructs the ProjectAssignment object by calling the ProjectAssignment() constructor using values from Builder instance
         * @return new instance of ProjectAssignment class
         */
        ProjectAssignment build();
    }

    /**
     * Helper class for constructing instances of ProjectAssignment
     */
    private static class Builder implements IdSetter, NameSetter, WorkerSetter, OptionalSetter {
        private Integer id;
        private String name;
        private ArrayList<String> workers;
        private Set<String> tasks;
        private Integer estimatedTime;

        @Override
        public NameSetter id(Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public WorkerSetter name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public OptionalSetter workers(List<String> workers) {
            this.workers = new ArrayList<>(workers);
            return this;
        }

        @Override
        public OptionalSetter taskSetter(Set<String> tasks) {
            this.tasks = tasks;
            return this;
        }

        @Override
        public OptionalSetter estimatedTimeSetter(Integer estimatedTime) {
            this.estimatedTime = estimatedTime;
            return this;
        }

        @Override
        public ProjectAssignment build() {
            return new ProjectAssignment(this);
        }
    }

    public List<String> getWorkers() {
        return workers;
    }
}
