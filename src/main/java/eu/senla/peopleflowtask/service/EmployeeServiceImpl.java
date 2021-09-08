package eu.senla.peopleflowtask.service;

import eu.senla.peopleflowtask.domain.Employee;
import eu.senla.peopleflowtask.domain.EmployeeState;
import eu.senla.peopleflowtask.exception.EmployeeNotFoundException;
import eu.senla.peopleflowtask.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee addEmployee(Employee employee) {
        employee.setState(EmployeeState.ADDED);
        Employee insertedEmployee = employeeRepository.insert(employee);
        log.debug("ADDED EMPLOYEE: " + insertedEmployee);
        return insertedEmployee;
    }

    @Override
    @Transactional
    public void updateEmployeeState(String id, EmployeeState newState) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if (employeeOptional.isEmpty()) {
            throw new EmployeeNotFoundException("Employee with id=" + id + " doesn't exist");
        }

        Employee employee = employeeOptional.get();
        employee.setState(newState);
    }
}
