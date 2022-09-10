package mk.ukim.finki.users.service;

import mk.ukim.finki.users.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void createUserTest() {
        this.userService.save("sara", "misajlovska","sara.m", "sm", new HashSet<>());
        //Assertions.assertEquals(1, this.userService.findAll().size());
        assertThat(this.userService.findAll().size()).isEqualTo(1);
    }

    @Test
    public void deleteUserTest() {
        User user = this.userService.findAll().stream().findFirst().get();
        this.userService.delete(user);
        //Assertions.assertEquals(1, this.userService.findAll().size());
        assertThat(this.userService.findAll().size()).isEqualTo(0);
    }
}
