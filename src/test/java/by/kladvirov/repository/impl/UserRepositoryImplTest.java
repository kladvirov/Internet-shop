package by.kladvirov.repository.impl;

import by.kladvirov.model.User;
import by.kladvirov.repository.CommonRepositoryTest;
import by.kladvirov.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryImplTest extends CommonRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest_ReturnsTheCopyFromDB() {

        User expected = findUserById();
        Optional<User> user = userRepository.findUserById(expected.getId());
        User actual = user.orElse(User.builder().build());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findByLoginTest_ReturnsTheCopyFromDB() {

        User expected = findUserById();
        Optional<User> user = userRepository.findByLogin(expected.getLogin());
        User actual = user.orElse(User.builder().build());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAllTest_ReturnsAllUserFromDB() {

        List<User> expected = findAllUsers();
        List<User> actual = userRepository.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void saveTest_ReturnsSavedUser() {

        User expected = User.builder().id(39L).name("Bichik").surname("Yurueu").birthDate(LocalDate.of(1980, 1, 1)).login("smth123").password("password123").isBlocked(false).build();
        User userForDataBase = User.builder().name("Bichik").surname("Yurueu").birthDate(LocalDate.of(1980, 1, 1)).login("smth123").password("password123").isBlocked(false).build();
        User actual = userRepository.save(userForDataBase);
        expected.setId(actual.getId());

        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(expected, userRepository.findById(expected.getId()).get());
    }

    @Test
    public void updateTest_ReturnsUpdatedUser() {

        User user = User.builder().id(1L).name("John").surname("Yurueu").birthDate(LocalDate.of(1980, 1, 1)).login("johndoe80").password("password123").isBlocked(false).build();
        userRepository.save(user);
        Optional<User> dataBaseUser = userRepository.findById(user.getId());

        Assertions.assertTrue(dataBaseUser.isPresent());
        Assertions.assertEquals(user, dataBaseUser.get());
    }

    @Test
    public void deleteTest_ReturnsDeletedUser() {

        User user = User.builder().id(1L).build();
        userRepository.deleteById(user.getId());

        Assertions.assertFalse(userRepository.findById(user.getId()).isPresent());
    }

}
