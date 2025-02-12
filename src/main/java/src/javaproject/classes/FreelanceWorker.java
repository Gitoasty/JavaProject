package src.javaproject.classes;

/**
 * This class is used to represent freelance workers in the system
 */
public final class FreelanceWorker extends Worker {
    private Contract contract;

    /**
     * Constructs the FreelanceWorker object
     * @param fw builder object used to construct the FreelanceWorker
     */
    public FreelanceWorker(Builder fw) {
        this.id = fw.id;
        this.firstName = fw.firstName;
        this.lastName = fw.lastName;
        this.workExperience = fw.workExperience;
        this.contract = fw.contract;
    }

    /**
     * ensures we go to IdSetter next, starting point of the Builder Pattern
     * @return Builder as an object of type IdSetter
     */
    public static IdSetter builder() {
        return new Builder();
    }

    /**
     * First step of the FreelanceWorker Builder process
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
     * Second step of the FreelanceWorker Builder process
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
     * Third step of the FreelanceWorker Builder process
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
     * Fourth step of the FreelanceWorker Builder process
     */
    public interface WorkExperienceSetter {

        /**
         * guides us to the next step of the Builder Pattern
         * @param workExperience takes an Integer to be set as work experiencce
         * @return Builder as an object of type ContractSetter
         */
        ContractSetter workExperience(Integer workExperience);
    }

    /**
     * Optional step of the FreelanceWorker Builder process, contains the build() method
     */
    public interface ContractSetter {

        /**
         * guides us back to this part of the Builder Pattern, so we can call the build() method
         * @param contract takes a Contract object to be set as the currently signed contract
         * @return Builder as an object of type ContractSetter
         */
        ContractSetter contract(Contract contract);

        /**
         * constructs the FreelanceWorker object by calling the FreelanceWorker() constructor using values from Builder instance
         * @return new instance of FreelanceWorker class
         */
        FreelanceWorker build();
    }

    /**
     * Helper class for constructing instances of FreelanceWorker
     */
    private static class Builder implements IdSetter, FirstNameSetter, LastNameSetter, WorkExperienceSetter, ContractSetter {
        private String id;
        private String firstName;
        private String lastName;
        private Integer workExperience;
        private Contract contract;

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
        public ContractSetter workExperience(Integer experience) {
            this.workExperience = experience;
            return this;
        }

        @Override
        public ContractSetter contract(Contract contract) {
            this.contract = contract;
            return this;
        }

        @Override
        public FreelanceWorker build() {
            return new FreelanceWorker(this);
        }
    }

    /**
     * Gets the contract
     * @return Contract object
     */
    public Contract contractGetter() {
        return contract;
    }
}
