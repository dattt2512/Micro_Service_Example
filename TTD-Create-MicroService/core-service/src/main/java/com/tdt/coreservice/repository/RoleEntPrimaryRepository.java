package com.tdt.coreservice.repository;

import com.tdt.coreservice.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntPrimaryRepository extends JpaRepository<RoleEntity, Long> {

    public RoleEntity findByName(String name);
}
