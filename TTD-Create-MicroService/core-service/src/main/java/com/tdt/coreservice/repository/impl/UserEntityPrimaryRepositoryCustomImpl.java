package com.tdt.coreservice.repository.impl;

import com.tdt.coreservice.entities.RoleEntity;
import com.tdt.coreservice.entities.UserEntity;
import com.tdt.coreservice.repository.UserEntityPrimaryRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class UserEntityPrimaryRepositoryCustomImpl implements UserEntityPrimaryRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> listAllExistUser() {
        String query = "select u from UserEntity u where u.deletedDate is null";
        List<UserEntity> existUsers = entityManager.createQuery(query)
                .getResultList();
        return existUsers;
    }
}
