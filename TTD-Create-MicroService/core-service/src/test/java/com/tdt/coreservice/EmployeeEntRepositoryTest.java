package com.tdt.coreservice;

import com.tdt.coreservice.entities.EmployeeEnt;
import com.tdt.coreservice.repository.EmployeeEntRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataMongoTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class EmployeeEntRepositoryTest {

    private static final String[] NAMES = { "Tom", "Jerry", "Donald" };

    @Autowired
    private EmployeeEntRepository empRepo;

    @Test
    public void testInsert() {
        EmployeeEnt employee = new EmployeeEnt();

        long id = this.empRepo.getMaxEmpId() + 1;
        int idx = (int) (id % NAMES.length);
        String fullName = NAMES[idx] + " " + id;

        employee.setId(id);
        employee.setEmpNo("E" + id);
        employee.setFullName(fullName);
        employee.setHireDate(new Date());
        this.empRepo.insert(employee);
    }
}
