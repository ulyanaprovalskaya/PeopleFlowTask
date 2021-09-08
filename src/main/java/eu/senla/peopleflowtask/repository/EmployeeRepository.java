package eu.senla.peopleflowtask.repository;

import eu.senla.peopleflowtask.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
