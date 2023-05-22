package com.tdt.coreservice.repository;

import com.tdt.coreservice.entities.EmployeeEnt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeEntRepository extends MongoRepository<EmployeeEnt, Long>, EmployeeEntRepositoryCustom {

    EmployeeEnt findByEmpNo(String empNo);
}
