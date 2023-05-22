package com.tdt.coreservice.repository;

import com.tdt.coreservice.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserEntityPrimaryRepository extends JpaRepository<UserEntity, Long>, UserEntityPrimaryRepositoryCustom{

    UserEntity findByEmail(String email);

    @Query(value = "select * from users where deleted_date is null", nativeQuery = true)
    List<UserEntity> listAllExistUser();
}
