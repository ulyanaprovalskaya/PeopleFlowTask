package eu.senla.peopleflowtask.config;

import eu.senla.peopleflowtask.statemachine.StateMachine;
import eu.senla.peopleflowtask.statemachine.StateMachineProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableConfigurationProperties(StateMachineProperties.class)
public class AppConfiguration {

    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public StateMachine stateMachine(StateMachineProperties stateMachineProperties) {
        StateMachine stateMachine = new StateMachine();
        stateMachine.setStates(stateMachineProperties.getStates());
        return stateMachine;
    }
}
