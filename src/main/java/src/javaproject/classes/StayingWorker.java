package src.javaproject.classes;

/**
 * This class is used to represent full-time workers at a company
 */
public final class StayingWorker extends Worker {

    /**
     * Constructs the StayingWorker object
     * @param fw builder object used to construct the StayingWorker
     */
    public StayingWorker(Builder fw) {
        this.id = fw.id;
        this.firstName = fw.firstName;
        this.lastName = fw.lastName;
        this.workExperience = fw.workExperience;
    }

    /**
     * ensures we go to IdSetter next, starting point of the Builder Pattern
     * @return Builder as an object of type IdSetter
     */
    public static IdSetter builder() {
        return new Builder();
    }

    /**
     * First step of the StayingWorker Builder process
     */
    public interface IdSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param id takes an String to be set as worker id
         * @return Builder as an object of type FirstNameSetter
         */
        FirstNameSetter id(String id);
    }

    /**
     * Second step of the StayingWorker Builder process
     */
    public interface FirstNameSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param firstName takes a String to be set as worker's first name
         * @return Builder as an object of type LastNameSetter
         */
        LastNameSetter firstName(String firstName);
    }

    /**
     * Third step of the StayingWorker Builder process
     */
    public interface LastNameSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param lastName takes a String to be set as worker's last name
         * @return Builder as an object of type WorkExperienceSetter
         */
        WorkExperienceSetter lastName(String lastName);
    }

    /**
     * Fourth step of the StayingWorker Builder process
     */
    public interface WorkExperienceSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param workExperience takes an Integer to be set as work experiencce
         * @return Builder as an object of type ContractSetter
         */
        FinalSetter workExperience(Integer workExperience);
    }

    /**
     * Optional step of the StayingWorker Builder process, contains the build() method
     */
    public interface FinalSetter {

        /**
         * constructs the StayingWorker object by calling the StayingWorker() constructor using values from Builder instance
         * @return new instance of StayingWorker class
         */
        StayingWorker build();
    }

    /**
     * Helper class for constructing instances of StayingWorker
     */
    private static class Builder implements IdSetter, FirstNameSetter, LastNameSetter, WorkExperienceSetter, FinalSetter {
        private String id;
        private String firstName;
        private String lastName;
        private Integer workExperience;

        @Override
        public FirstNameSetter id(String id) {
            this.id = id;
            return this;
        }

        @Override
        public LastNameSetter firstName(String name) {
            this.firstName = name;
            return this;
        }

        @Override
        public WorkExperienceSetter lastName(String name) {
            this.lastName = name;
            return this;
        }

        @Override
        public FinalSetter workExperience(Integer experience) {
            this.workExperience = experience;
            return this;
        }

        @Override
        public StayingWorker build() {
            return new StayingWorker(this);
        }
    }
}
