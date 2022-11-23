import com.exampleMainService.MainServiceApplication;
import com.exampleMainService.clients.EmployeeClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainServiceApplication.class)
@Rollback
class EmployeeClientTest {
    @Autowired
    EmployeeClient client;
    Logger LOGGER = LoggerFactory.getLogger(EmployeeClientTest.class);
    String username = "admin";

    @Test
    void getAllEmployeesTest() {
        String response = client.getEmployees(username);
        LOGGER.info("Logging Response with: "+response);
        assertThat(response).isNotNull();
    }
    @Test
    void getEmployeeByIdTest() {
        String response = client.getEmployeeById(6, username);
        LOGGER.info("Logging Response with: "+response);
        assertThat(response).doesNotContain("\"id\":0");
    }
    @Test
    void saveEmployeeTest() {
        String json = "{\"id\":0,\"name\":\"SaadMunir\",\"email\":\"saadmunirmr8886@gmail.com\",\"password\":\"123\",\"salary\":2000.0,\"department\":{\"id\":6,\"name\":\"Finance\"}}";
        String response = client.saveEmployee(json, username);
        LOGGER.info("Logging Response with: "+response);
        assertThat(response).doesNotContain("\"id\":0");
    }
    @Test
    void updateEmployeeTest() {
        String json = "{\"id\":170,\"name\":\"SaadMunir\",\"email\":\"saadmunirmr8886@gmail.com\",\"password\":\"123\",\"salary\":2000.0,\"department\":{\"id\":6,\"name\":\"Finance\"}}";
        String response = client.updateEmployee(json, username);
        LOGGER.info("Logging Response with: "+response);
        assertThat(response).doesNotContain("\"id\":0");
    }
    @Test
    void deleteEmployeeTest() {
        String response = client.deleteEmployee(170, username);
        LOGGER.info("Logging Response with: " + response);
        assertThat(response).doesNotContain("Employee is not successfully deleted");
    }
}
