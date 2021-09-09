package eu.senla.peopleflowtask.config;

import eu.senla.peopleflowtask.config.properties.StateMachineProperties;
import eu.senla.peopleflowtask.domain.EmployeeState;
import eu.senla.peopleflowtask.statemachine.EmployeeStateStructure;
import eu.senla.peopleflowtask.statemachine.StateMachine;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(StateMachineProperties.class)
public class AppConfiguration {

    @Bean
    public StateMachine stateMachine(StateMachineProperties stateMachineProperties) {
        StateMachine stateMachine = new StateMachine();

        Map<EmployeeState, EmployeeStateStructure> states = stateMachineProperties.getStates()
                .stream()
                .collect(Collectors.toMap(EmployeeStateStructure::getCurrent, Function.identity()));

        stateMachine.setStates(states);
        return stateMachine;
    }
}
