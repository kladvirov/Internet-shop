package by.kladvirov.repository;

import by.kladvirov.model.User;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class CommonRepositoryTest {

    private List<User> users;

    public CommonRepositoryTest() {
        fillUsers();
    }

    private void fillUsers() {
        users = new ArrayList<>() {{
            add(User.builder().id(1L).name("John").surname("Doe").birthDate(LocalDate.of(1980, 1, 1)).login("johndoe80").password("password123").isBlocked(false).build());
            add(User.builder().id(2L).name("Jane").surname("Smith").birthDate(LocalDate.of(1990, 2, 2)).login("janesmith90").password("password456").isBlocked(true).build());
            add(User.builder().id(34L).name("Egor").surname("Lelenik").birthDate(LocalDate.of(2005, 4, 28)).login("bigBoy").password("$2a$10$ckvAvu8vXiwu5JSB95p6Mui.A9/amCOP6V57vuq0mu0Yw8ctdLPsW").isBlocked(false).build());
        }};
    }

    public User findUserById() {
        return users.get(1);
    }

    public List<User> findAllUsers() {
        return users;
    }

}
