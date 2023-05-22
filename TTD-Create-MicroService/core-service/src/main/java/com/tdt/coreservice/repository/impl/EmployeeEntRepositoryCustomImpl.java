package com.tdt.coreservice.repository.impl;

import com.mongodb.client.result.UpdateResult;
import com.tdt.coreservice.entities.EmployeeEnt;
import com.tdt.coreservice.repository.EmployeeEntRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

public class EmployeeEntRepositoryCustomImpl implements EmployeeEntRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long getMaxEmpId() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.limit(1);
        EmployeeEnt maxObject = mongoTemplate.findOne(query, EmployeeEnt.class);
        if (maxObject == null) {
            return 0L;
        }
        return maxObject.getId();
    }
}
