package src.javaproject.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This class is used for storing assigned projects
 */
public class ProjectAssignment extends Project {
    private ArrayList<Worker> workers;

    /**
     * constructs the ProjectAssignment class
     * @param p builder object used to construct the ProjectAssignment
     */
    public ProjectAssignment(Builder p) {
        this.id = p.id;
        this.name = p.name;
        this.workers = p.workers;
        this.tasks = p.tasks;
        this.estimatedTime = p.estimatedTime;
    }

    /**
     * ensures we go to NameSetter next, starting point of the Builder Pattern
     * @return Builder as an object of type IdSetter
     */
    public static ProjectAssignment.IdSetter builder() {
        return new Builder();
    }

    /**
     * First step of the Builder process
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
     * Second step of the Builder process
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
     * Third step of the Builder process
     */
    public interface WorkerSetter {

        /**
         * guides us to the final step of the Builder Pattern
         * @param workers takes a List of Strings to be set as the project workers
         * @return Builder as an object of type OptionalSetter
         */
        OptionalSetter workers(List<Worker> workers);
    }

    /**
     * Optional part of the Builder process, also contains the build() method
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
        private ArrayList<Worker> workers;
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
        public OptionalSetter workers(List<Worker> workers) {
            this.workers = (ArrayList<Worker>) workers;
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

    /**
     * takes an Arraylist of Workers for the Project
     * @param w
     */
    public void workerSetter(List<Worker> w) {
        this.workers = (ArrayList<Worker>) w;
    }

    /**
     * used to get the workers assigned to the project
     * @return an Arraylist of Workers working on the project
     */
    public List<Worker> workerGetter() {
        return workers;
    }
}
