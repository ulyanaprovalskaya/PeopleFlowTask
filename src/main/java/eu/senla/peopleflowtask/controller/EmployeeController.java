package eu.senla.peopleflowtask.controller;

import eu.senla.peopleflowtask.domain.Employee;
import eu.senla.peopleflowtask.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateState(@PathVariable String id, @RequestBody Employee employee){
        employeeService.updateEmployeeState(id, employee.getState());
        return ResponseEntity.noContent().build();
    }
}
