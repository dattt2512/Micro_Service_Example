package com.tdt.coreservice.repository;

import com.tdt.coreservice.entities.RoleEntity;
import com.tdt.coreservice.entities.UserEntity;

import java.util.List;

public interface UserEntityPrimaryRepositoryCustom {

    List<UserEntity> listAllExistUser();
}
