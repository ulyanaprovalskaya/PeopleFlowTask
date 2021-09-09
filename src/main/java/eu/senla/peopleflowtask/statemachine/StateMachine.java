package eu.senla.peopleflowtask.statemachine;

import eu.senla.peopleflowtask.domain.EmployeeState;
import eu.senla.peopleflowtask.exception.CannotChangeStateException;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class StateMachine {

    private Map<EmployeeState, EmployeeStateStructure> states;

    public boolean canChangeState(EmployeeState currentState, EmployeeState targetState) {
        EmployeeStateStructure current = states.get(currentState);

        if (current == null) {
            throw new CannotChangeStateException(String.format("There is no such state: %s", currentState));
        }

        return current.getNext().equals(targetState) || current.getPrevious().equals(targetState);
    }

}
