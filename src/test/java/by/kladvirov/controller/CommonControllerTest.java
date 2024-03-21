package by.kladvirov.controller;

import by.kladvirov.mapper.RoleMapper;
import by.kladvirov.repository.AuthorityRepository;
import by.kladvirov.repository.GoodRepository;
import by.kladvirov.repository.OrderRepository;
import by.kladvirov.repository.RoleRepository;
import by.kladvirov.repository.UserRepository;
import by.kladvirov.service.RoleService;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class, DataSourceAutoConfiguration.class})
@MockBean(classes = {
        RoleRepository.class,
        AuthorityRepository.class,
        UserRepository.class,
        GoodRepository.class,
        OrderRepository.class
})
@AutoConfigureMockMvc
public class CommonControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected RoleService roleService;

    @Spy
    protected RoleMapper roleMapper;

}
