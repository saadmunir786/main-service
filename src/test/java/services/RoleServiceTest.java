package services;

import com.exampleMainService.MainServiceApplication;
import com.exampleMainService.entities.Role;
import com.exampleMainService.services.RoleService;
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
class RoleServiceTest {
    @Autowired
    RoleService service;
    Logger LOGGER = LoggerFactory.getLogger(RoleServiceTest.class);

    @Test
    void getRolesTest() {
        assertThat(service.roles()).size().isGreaterThan(0);
    }
    @Test
    void saveRole() {
        Role role = new Role();
        role.setId(0);
        role.setRole("newrole");
        role.setPermissions("GET");


        assertThat(service.save(role)).isInstanceOf(Role.class).isEqualTo(role);
    }
    @Test
    void updateRole() {
        Role role = new Role();
        role.setId(1106);
        role.setRole("newrole");
        role.setPermissions("POST");

        assertThat(service.save(role)).isInstanceOf(Role.class).isEqualTo(role);
    }
//    @Test
//    void deleteUser() {
//        String username = "test";
//
//        assertThat(service.deleteByUsername(username)).isTrue();
//    }
}
