package eu.senla.peopleflowtask.service;

import eu.senla.peopleflowtask.domain.Employee;
import eu.senla.peopleflowtask.domain.EmployeeState;
import eu.senla.peopleflowtask.exception.CannotChangeStateException;
import eu.senla.peopleflowtask.exception.EmployeeNotFoundException;
import eu.senla.peopleflowtask.repository.EmployeeRepository;
import eu.senla.peopleflowtask.statemachine.StateMachine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final StateMachine stateMachine;

    @Override
    public Employee addEmployee(Employee employee) {
        employee.setState(EmployeeState.ADDED);
        Employee insertedEmployee = employeeRepository.insert(employee);
        log.debug("ADDED EMPLOYEE: " + insertedEmployee);
        return insertedEmployee;
    }

    @Override
    public void updateEmployeeState(String id, EmployeeState targetState) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(String.format("Employee with id=%s doesn't exist", id)));

        if(!employee.getState().equals(targetState) && stateMachine.canChangeState(employee.getState(), targetState)) {
            employee.setState(targetState);
            employeeRepository.save(employee);
        }
        else throw new CannotChangeStateException(String.format("Cannot change employee state from %s to %s", employee.getState(), targetState));
    }
}
