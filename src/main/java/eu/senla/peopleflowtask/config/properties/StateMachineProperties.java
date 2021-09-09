package eu.senla.peopleflowtask.config.properties;

import eu.senla.peopleflowtask.statemachine.EmployeeStateStructure;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "statemachine")
@Getter
@Setter
public class StateMachineProperties {

    private List<EmployeeStateStructure> states;

}
