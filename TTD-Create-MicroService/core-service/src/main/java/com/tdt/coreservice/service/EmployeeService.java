package com.tdt.coreservice.service;

import com.tdt.coreservice.dto.response.EmployeeDTO;
import com.tdt.coreservice.entities.EmployeeEnt;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    EmployeeDTO insert(EmployeeDTO empDTO);

    List<EmployeeDTO> listAll();

    EmployeeDTO update(Long id, EmployeeDTO empDTO);

    Optional<EmployeeEnt> findById(Long id);

    void deleteEmp(Long id);
}
