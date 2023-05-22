package com.tdt.coreservice;

import com.tdt.coreservice.entities.Role;
import com.tdt.coreservice.entities.RoleEntity;
import com.tdt.coreservice.repository.RoleEntPrimaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleEntityRepositoryTest {

    @Autowired
    private RoleEntPrimaryRepository roleRepo;

    @Test
    public void createRoleTest() {
        RoleEntity userRoleCreate = new RoleEntity();
        userRoleCreate.setName(Role.USER.toString());

        roleRepo.save(userRoleCreate);

        RoleEntity userRole = roleRepo.findByName(Role.USER.toString());

        assertThat(userRole).isNotNull();
    }

    @Test
    public void retrieveRoleTest() {
        RoleEntity adminRole = roleRepo.findByName(Role.ADMIN.toString());

        assertThat(adminRole).isNotNull();
    }

    @Test
    public void deleteRoleTest() {
        RoleEntity userRole = roleRepo.findByName(Role.USER.toString());

        roleRepo.deleteById(userRole.getId());

        assertThat(roleRepo.findByName(Role.USER.toString())).isNull();
    }
}
