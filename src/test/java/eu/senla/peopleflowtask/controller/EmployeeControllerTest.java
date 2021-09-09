package eu.senla.peopleflowtask.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.senla.peopleflowtask.domain.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmployeeControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private Employee employee;

    @Autowired
    public EmployeeControllerTest(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    void beforeAll() {
        employee = new Employee();
        employee.setFirstName("abc");
        employee.setLastName("def");
        employee.setAge(31);
        employee.setContractInformation("qwerty");
    }

    @Test
    void addEmployee_returnsEmployeeAndOkStatus() throws Exception {
        String requestBody = objectMapper.writeValueAsString(employee);

        MvcResult result = mockMvc.perform(post("/employees")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Employee insertedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);
        assertNotNull(insertedEmployee);
    }

    @Test
    void updateState_changesEmployeeState() throws Exception {
        String requestBody = objectMapper.writeValueAsString(employee);

        MvcResult result = mockMvc.perform(post("/employees")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        Employee insertedEmployee = objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);

        mockMvc.perform(patch("/employees/" + insertedEmployee.getId())
                .content("{\"state\": \"IN_CHECK\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}