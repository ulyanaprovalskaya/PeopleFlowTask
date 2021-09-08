package eu.senla.peopleflowtask.service;

import eu.senla.peopleflowtask.domain.Employee;
import eu.senla.peopleflowtask.domain.EmployeeState;

public interface EmployeeService {

    Employee addEmployee(Employee employee);

    void updateEmployeeState(String id, EmployeeState newState);
}
