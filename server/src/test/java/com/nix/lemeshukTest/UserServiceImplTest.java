package com.nix.lemeshukTest;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nix.lemeshuk.config.RootConfiguration;
import com.nix.lemeshuk.model.Role;
import com.nix.lemeshuk.model.User;
import com.nix.lemeshuk.dao.UserService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "sa",
        password = "", schema = "PUBLIC")
@ContextConfiguration(classes = {RootConfiguration.class})
@WebAppConfiguration
@ComponentScan("com.nix.lemeshuk.dao")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private User user;

    @Before
    public void beforeTest() {
        user = new User();
    }

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    @ExpectedDataSet(value = "dataset/user/create-user.xml", ignoreCols = {"role_id", "password"})
    public void shouldCreateUser() {
        user.setRole(new Role(1L, "admin"));
        user.setUsername("Admin");
        user.setPassword("myPassword");
        user.setEmail("myemail2@gmail.com");
        user.setFirstName("login");
        user.setLastName("login");
        user.setBirthday(LocalDate.of(2000, 6, 20));

        userService.create(user);
    }

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    @ExpectedDataSet(value = "dataset/user/delete-user.xml")
    public void shouldremoveRole() {
        user.setId(1L);
        user.setRole(new Role(1L, "admin"));
        user.setUsername("myLogin");
        user.setPassword("myPassword");
        user.setEmail("myemail@gmail.com");
        user.setFirstName("login");
        user.setLastName("login");
        user.setBirthday(LocalDate.of(2000, 5, 20));

        userService.delete(user);
    }

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    @ExpectedDataSet(value = "dataset/user/update-user.xml", ignoreCols = "password")
    public void shouldupdate() {
        user.setId(1L);
        user.setRole(new Role(1L, "admin"));
        user.setUsername("myLogin");
        user.setPassword("updatedPassword");
        user.setEmail("updatedEmail@gmail.com");
        user.setFirstName("login");
        user.setLastName("login");
        user.setBirthday(LocalDate.of(2000, 6, 20));

        userService.update(user);
    }

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void shouldfindAll() {
        List<User> userList = new ArrayList<>();

        User firstUser = new User(1L, new Role(1L, "admin"),
                "myLogin", "myPassword", "myemail@gmail.com", "login", "login",
                (LocalDate.of(2000, 06, 20)));
        User secondUser = new User(2L, new Role(1L, "admin"),
                "Moderator", "1234", "email2@gmail.com", "FirstName2", "LastName2",
                (LocalDate.of(2015, 01, 01)));

        userList.add(firstUser);
        userList.add(secondUser);

        assertEquals(userList, userService.findAll());
    }

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void shouldfindUserById() {
        user.setId(1L);
        user.setRole(new Role(1L, "admin"));
        user.setUsername("myLogin");
        user.setPassword("myPassword");
        user.setEmail("myemail@gmail.com");
        user.setFirstName("login");
        user.setLastName("login");
        user.setBirthday((LocalDate.of(2000, 06, 20)));

        assertEquals(user, userService.findById(1L).get());
    }

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void shouldfindUserByUsername() {
        user.setId(2L);
        user.setRole(new Role(1L, "admin"));
        user.setUsername("Moderator");
        user.setPassword("1234");
        user.setEmail("email2@gmail.com");
        user.setFirstName("FirstName2");
        user.setLastName("LastName2");
        user.setBirthday((LocalDate.of(2015, 01, 01)));

        assertEquals(user, userService.findByUsername(user.getUsername()).get());
    }

    @Test
    @DataSet(value = "dataset/user/init-user.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void shouldfindUserByEmail() {
        user.setId(2L);
        user.setRole(new Role(1L, "admin"));
        user.setUsername("Moderator");
        user.setPassword("1234");
        user.setEmail("email2@gmail.com");
        user.setFirstName("FirstName2");
        user.setLastName("LastName2");
        user.setBirthday((LocalDate.of(2015, 01, 01)));

        assertEquals(user, userService.findByEmail("email2@gmail.com").get());
    }
}
