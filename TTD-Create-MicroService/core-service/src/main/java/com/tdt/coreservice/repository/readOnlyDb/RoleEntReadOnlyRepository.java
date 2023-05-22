package com.tdt.coreservice.repository.readOnlyDb;

import com.tdt.coreservice.anotation.ReadOnlyRepository;
import com.tdt.coreservice.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ReadOnlyRepository
public interface RoleEntReadOnlyRepository extends JpaRepository<RoleEntity, Long> {
}
