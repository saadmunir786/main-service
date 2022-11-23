package services;

import com.exampleMainService.MainServiceApplication;
import com.exampleMainService.entities.User;
import com.exampleMainService.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainServiceApplication.class)
@Rollback
class UserServiceTest {
    @Autowired
    UserService service;
    Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

    @Test
    void getUsersTest() {
        assertThat(service.users()).size().isGreaterThan(0);
    }
    @Test
    void saveUser() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@gmail.com");
        user.setEnabled(true);
        user.setPassword("123");

        assertThat(service.save(user)).isInstanceOf(User.class).isEqualTo(user);
    }
    @Test
    void updateUser() {
        User user = new User();
        user.setUsername("test");
        user.setEmail("test@gmail.com");
        user.setEnabled(true);
        user.setPassword("456");

        assertThat(service.save(user)).isInstanceOf(User.class).isEqualTo(user);
    }
    @Test
    void deleteUser() {
        String username = "test";
        try {
            assertThat(service.deleteByUsername(username)).isTrue();
        }catch (EmptyResultDataAccessException ex){
            LOGGER.error("Error: "+ex.getMessage());
        }


    }
}
