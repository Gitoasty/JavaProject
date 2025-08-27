package src.javaproject.classes;

import lombok.Getter;

import java.util.Set;

/**
 * Class to represent a company with workers
 */
@Getter
public final class Company {
    private final Integer id;
    private final String name;
    private final Set<String> workers;

    /**
     * Constructs the Company object
     * @param c Builder object used to construct Company
     */
    private Company(Builder c) {
        this.id = c.id;
        this.name = c.name;
        this.workers = c.workers;
    }

    /**
     * ensures we go to WorkerSetter next
     * @return Builder as an object of type IdSetter
     */
    public static IdSetter builder() {
        return new Builder();
    }

    /**
     * First step of the Company Builder process
     */
    public interface IdSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param id takes an Integer to be set as company id
         * @return Builder as an object of type NameSetter
         */
        NameSetter id(Integer id);
    }

    /**
     * Second step of the Company Builder process
     */
    public interface NameSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param name takes a String to be set as company name
         * @return Builder as an object of type WorkerSetter
         */
        WorkerSetter name(String name);
    }

    /**
     * Third step of the Company Builder process
     */
    public interface WorkerSetter {

        /**
         * guides us to the final step of the Builder Pattern
         * @param workers takes a Set of Workers to be set as company workers
         * @return Builder as an object of type FinalSetter
         */
        FinalSetter workers(Set<String> workers);
    }

    /**
     * Final step of the Builder process
     */
    public interface FinalSetter {

        /**
         * constructs the Company object by calling the Company() constructor using values from Builder instance
         * @return new instance of Company class
         */
        Company build();
    }

    /**
     * Helper class for constructing instances of Company
     */
    private static class Builder implements IdSetter, NameSetter, WorkerSetter, FinalSetter {
        private Integer id;
        private String name;
        private Set<String> workers;

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
        public FinalSetter workers(Set<String> workers) {
            this.workers = workers;
            return this;
        }

        @Override
        public Company build() {
            return new Company(this);
        }
    }
}
