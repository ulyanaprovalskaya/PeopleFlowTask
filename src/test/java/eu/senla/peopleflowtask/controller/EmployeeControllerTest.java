package eu.senla.peopleflowtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.peopleflowtask.domain.Employee;
import eu.senla.peopleflowtask.domain.EmployeeState;
import eu.senla.peopleflowtask.dto.EmployeeStateDto;
import eu.senla.peopleflowtask.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final EmployeeRepository employeeRepository;
    private Employee employee;

    @Autowired
    public EmployeeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, EmployeeRepository employeeRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.employeeRepository = employeeRepository;
    }

    @BeforeAll
    void beforeAll() {
        employee = new Employee();
        employee.setFirstName("abc");
        employee.setLastName("def");
        employee.setAge(31);
        employee.setContractInformation("qwerty");
    }

    @AfterEach
    void cleanDatabase(){
        employeeRepository.deleteAll();
    }


    @Test
    void addEmployee_returnsEmployeeAndOkStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(employee);

        MvcResult result = mockMvc.perform(post("/employees")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Employee expectedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);
        Employee actualEmployee = employeeRepository.findById(expectedEmployee.getId()).orElse(new Employee());

        assertEquals(expectedEmployee.getState(), actualEmployee.getState());
        assertEquals(expectedEmployee.getFirstName(), actualEmployee.getFirstName());
        assertEquals(expectedEmployee.getLastName(), actualEmployee.getLastName());
        assertEquals(expectedEmployee.getAge(), actualEmployee.getAge());
        assertEquals(expectedEmployee.getContractInformation(), actualEmployee.getContractInformation());
    }

    @ParameterizedTest
    @EnumSource(value = EmployeeState.class, names = {"ADDED", "APPROVED"})
    void updateState_sendsCorrectStateAndId_changesEmployeeStateAndReturnsOkStatus(EmployeeState targetState) throws Exception {
        employee.setState(EmployeeState.IN_CHECK);
        Employee insertedEmployee = employeeRepository.save(employee);

        String requestBody = objectMapper.writeValueAsString(new EmployeeStateDto(targetState));

        mockMvc.perform(patch("/employees/" + insertedEmployee.getId())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Employee updatedEmployee = employeeRepository.findById(insertedEmployee.getId()).orElse(new Employee());
        assertEquals(updatedEmployee.getState(), targetState);
    }

    @ParameterizedTest
    @EnumSource(value = EmployeeState.class, names = {"APPROVED", "ACTIVE"})
    void updateState_sendsIncorrectState_ReturnsNotFoundStatus(EmployeeState targetState) throws Exception{
        employee.setState(EmployeeState.ADDED);
        Employee insertedEmployee = employeeRepository.save(employee);

        String requestBody = objectMapper.writeValueAsString(new EmployeeStateDto(targetState));

        mockMvc.perform(patch("/employees/" + insertedEmployee.getId())
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateState_sendsIncorrectId_throwsEmployeeNotFoundExceptionAndReturnsNotFoundStatus() throws Exception{
        String requestBody = objectMapper.writeValueAsString(new EmployeeStateDto(EmployeeState.ADDED));
        String incorrectId = "123";

        mockMvc.perform(patch("/employees/" + incorrectId)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}