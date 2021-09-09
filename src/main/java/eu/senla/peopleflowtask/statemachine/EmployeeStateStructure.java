package eu.senla.peopleflowtask.statemachine;

import eu.senla.peopleflowtask.domain.EmployeeState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeStateStructure {

    private EmployeeState current;
    private EmployeeState next;
    private EmployeeState previous;
}
