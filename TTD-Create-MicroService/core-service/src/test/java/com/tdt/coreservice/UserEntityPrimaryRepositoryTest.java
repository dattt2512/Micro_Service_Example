package com.tdt.coreservice;

import com.tdt.coreservice.entities.UserEntity;
import com.tdt.coreservice.entities.Role;
import com.tdt.coreservice.entities.RoleEntity;
import com.tdt.coreservice.repository.UserEntityPrimaryRepository;
import com.tdt.coreservice.repository.RoleEntPrimaryRepository;
import com.tdt.coreservice.repository.readOnlyDb.UserEntityReadOnlyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserEntityPrimaryRepositoryTest {

    @Autowired
    UserEntityPrimaryRepository userRepo;

    @Autowired
    RoleEntPrimaryRepository roleRepo;

    @Test
    public void createUserTest() {
        UserEntity newUser = new UserEntity();
        newUser.setUsername("dattran");
        newUser.setEmail("dattranbeyourself01@gmail.com");
        newUser.setEnabled(true);
        newUser.setCreatedDate(new Date());

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String encrytedPassword = bCrypt.encode("12345");

        newUser.setPassword(encrytedPassword);

        RoleEntity userRole = roleRepo.findByName(Role.USER.toString());
        newUser.setRoles(Arrays.asList(userRole));

        UserEntity createdUser = userRepo.save(newUser);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isGreaterThan(0);
    }

    @Test
    public void retrieveTest() {
        Long findingUserId = 1L;

        Optional<UserEntity> findingUsers = userRepo.findById(findingUserId);

        assertThat(findingUsers).isNotNull();
    }

    @Test
    public void updateTest() {
        Long findingUserId = 3L;

        Optional<UserEntity> findingUsers = userRepo.findById(findingUserId);

        UserEntity foundUser = findingUsers.get();

        foundUser.setUpdatedDate(new Date());

        UserEntity checkingUser = userRepo.save(foundUser);

        assertThat(checkingUser.getUpdatedDate()).isNotNull();
    }

    @Test
    public void deleteTest() {
        Long findingUserId = 4L;

        Optional<UserEntity> findingUsers = userRepo.findById(findingUserId);

        UserEntity foundUser = findingUsers.get();

        foundUser.setDeletedDate(new Date());

        UserEntity deletedUser = userRepo.save(foundUser);

        assertThat(deletedUser.getDeletedDate()).isNotNull();
    }
}
