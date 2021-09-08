package eu.senla.peopleflowtask.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private int age;
    private String contractInformation;
    private EmployeeState state;
}
