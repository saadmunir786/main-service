import com.employeesService.entities.Department;
import com.employeesService.entities.Employee;
import com.exampleMainService.MainServiceApplication;
import com.exampleMainService.clients.DepartmentClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainServiceApplication.class)
@Rollback
class DepartmentClientTest{
    @Autowired
    DepartmentClient client;
    Logger LOGGER = LoggerFactory.getLogger(DepartmentClientTest.class);
    ObjectMapper mapper = new ObjectMapper();

    @Test
    void getDepartmentByName() {
        String validDeptName = "HR";
        String response = client.getDepartment(validDeptName);
        LOGGER.info("Logging Response with Valid Department Name: "+response);
        assertThat(response).isNotNull();
    }
    @Test
    void saveDepartmentTest() throws Exception{
        Department dept = new Department(0, "newDep");
        String username = "admin";
        String response = client.saveDepartment(mapper.writeValueAsString(dept), username);
        LOGGER.info("Logging Response: "+response);
        assertThat(response).isNotNull();
    }
    @Test
    void updateDepartmentTest() {
        String json = "{\"id\":16,\"name\":\"newDepartments\"}";
        String username = "admin";
        String response = client.updateDepartment(json, username);
        LOGGER.info("Logging Response: "+response);
        assertThat(response).isNotNull();
    }
    @Test
    void deleteDepartmentTest() {
        String json = "{\"id\":16,\"name\":\"newDepartments\"}";
        String username = "admin";
        String response = client.deleteDepartment(16, username);
        LOGGER.info("Logging Response: "+response);
        assertThat(response).isNotNull();
    }
    @Test
    void getDepartmentByIdTest() {
        String username = "admin";
        String response = client.getDepartmentById(1, username);
        LOGGER.info("Logging Response: "+response);
        assertThat(response).isNotNull();
    }






}
