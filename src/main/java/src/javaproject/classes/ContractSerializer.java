package src.javaproject.classes;

import src.javaproject.interfaces.SerializeMarker;

import java.time.LocalDate;

public final class ContractSerializer implements SerializeMarker {
    private final Integer id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Integer salary;
    private final Integer companyId;

    public ContractSerializer(Contract c) {
        this.id = c.id();
        this.startDate = c.startDate();
        this.endDate = c.endDate();
        this.salary = c.salary();
        this.companyId = c.companyId();
    }

    public ContractSerializer(Integer id, LocalDate startDate, LocalDate endDate, Integer salary, Integer companyId) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.salary = salary;
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return STR."\{id} - \{startDate} - \{endDate} - \{salary} - \{companyId}";
    }
}
