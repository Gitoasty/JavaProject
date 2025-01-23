package src.javaproject.classes;

import java.util.Set;

/**
 * This class is used for projects that have not yet been approved by the admin
 */
public class ProjectDraft extends Project {

    /**
     * constructs the ProjectDraft class
     * @param p builder object used to construct the ProjectDraft
     */
    public ProjectDraft(Builder p) {
        this.id = p.id;
        this.name = p.name;
        this.tasks = p.tasks;
        this.estimatedTime = p.estimatedTime;
    }

    /**
     * ensures we go to NameSetter next, starting point of the Builder Pattern
     * @return Builder as an object of type IdSetter
     */
    public static IdSetter builder() {
        return new Builder();
    }

    /**
     * First step of the ProjectDraft Builder process
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
     * Second step of the ProjectDraft Builder process
     */
    public interface NameSetter {

        /**
         * guides us to the optional step of the Builder Pattern
         * @param name takes a String to be set as project name
         * @return Builder as an object of type OptionalSetter
         */
        OptionalSetter name(String name);
    }

    /**
     * Optional part of the ProjectDraft Builder process, also contains the build() method
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
         * constructs the ProjectDraft object by calling the ProjectDraft() constructor using values from Builder instance
         * @return new instance of ProjectDraft class
         */
        ProjectDraft build();
    }

    /**
     * Helper class for constructing instances of ProjectDraft
     */
    private static class Builder implements IdSetter, NameSetter, OptionalSetter {
        private Integer id;
        private String name;
        private Set<String> tasks;
        private Integer estimatedTime;

        @Override
        public NameSetter id(Integer id) {
            this.id = id;
            return this;
        }

        @Override
        public OptionalSetter name(String name) {
            this.name = name;
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
        public ProjectDraft build() {
            return new ProjectDraft(this);
        }
    }
}
