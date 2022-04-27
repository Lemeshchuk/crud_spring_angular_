package com.nix.lemeshukTest;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nix.lemeshuk.config.RootConfiguration;
import com.nix.lemeshuk.model.Role;
import com.nix.lemeshuk.dao.RoleService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@DBUnit(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver", user = "sa",
        password = "", schema = "PUBLIC")
@ContextConfiguration(classes = {RootConfiguration.class})
@WebAppConfiguration
@ComponentScan("com.nix.lemeshuk.dao")
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    private Role role;

    @Before
    public void beforeTest() {
        role = new Role();
    }

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.instance();

    @Test
    @DataSet(value = "dataset/role/init-role-create.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    @ExpectedDataSet(value = "dataset/role/create-role.xml")
    public void createRole() {
        role.setName("admin3");
        roleService.create(role);
    }

    @Test
    @DataSet(value = "dataset/role/init-role.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    @ExpectedDataSet(value = "dataset/role/delete-role.xml")
    public void removeRole() {
        role.setId(3L);
        role.setName("admin3");

        roleService.remove(role);
    }

    @Test
    @DataSet(value = "dataset/role/init-role.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    @ExpectedDataSet(value = "dataset/role/update-role.xml")
    public void update() {
        role.setId(1L);
        role.setName("updatedRole");

        roleService.update(role);
    }

    @Test
    @DataSet(value = "dataset/role/init-role.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void findAll()  {
        List<Role> roleList = Arrays.asList(new Role(1L, "admin"),
                new Role(2L, "admin2"), new Role(3L, "admin3"));

        assertEquals(roleList, roleService.findAll());
    }

    @Test
    @DataSet(value = "dataset/role/init-role.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void findRoleById()  {
        role.setId(3L);
        role.setName("admin3");
        System.out.println(roleService.findById(3L));

        assertEquals(role, roleService.findById(3L).get());
    }

    @Test
    @DataSet(value = "dataset/role/init-role.xml", executeScriptsBefore = "script/init_tables.sql",
            executeScriptsAfter = "script/drop_tables.sql")
    public void findByName()  {
        role.setId(3L);
        role.setName("admin3");

        assertEquals(role, roleService.findByName("admin3").get());
    }
}