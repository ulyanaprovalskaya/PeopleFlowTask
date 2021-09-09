package eu.senla.peopleflowtask.statemachine;

import eu.senla.peopleflowtask.domain.EmployeeState;
import eu.senla.peopleflowtask.exception.CannotChangeStateException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StateMachine {

    private List<EmployeeStateStructure> states;

    public boolean canChangeState(EmployeeState currentState, EmployeeState targetState){
        EmployeeStateStructure current = states.stream()
                .filter(state -> state.getCurrent().equals(currentState))
                .findFirst()
                .orElseThrow(() -> new CannotChangeStateException(String.format("There is no such state: %s", currentState)));

        return current.getNext().equals(targetState) || current.getPrevious().equals(targetState);
    }

}
