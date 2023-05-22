package com.tdt.coreservice.service.impl;

import com.tdt.coreservice.dto.response.EmployeeDTO;
import com.tdt.coreservice.dto.response.UserDTO;
import com.tdt.coreservice.entities.EmployeeEnt;
import com.tdt.coreservice.entities.UserEntity;
import com.tdt.coreservice.mapper.BaseMapper;
import com.tdt.coreservice.repository.EmployeeEntRepository;
import com.tdt.coreservice.service.EmployeeService;
import com.tdt.coreservice.utils.DataUtis;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private static final BaseMapper<EmployeeEnt, EmployeeDTO> mapper = new BaseMapper<>(EmployeeEnt.class, EmployeeDTO.class);
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeEntRepository empRepo;

    @Override
    public EmployeeDTO insert(EmployeeDTO empDTO) {
        EmployeeEnt empFindByEmpNo = empRepo.findByEmpNo(empDTO.getEmpNo());

        if (!DataUtis.isNullOrEmpty(empFindByEmpNo)) {
            throw new RuntimeException("EmpNo already exists, please try with another EmpNo");
        }

        EmployeeEnt saveEmp = mapper.toPersistenceBean(empDTO);
        Long id = empRepo.getMaxEmpId() + 1;
        saveEmp.setId(id);

        return mapper.toDtoBean(empRepo.save(saveEmp));
    }

    @Override
    public EmployeeDTO update(Long id, EmployeeDTO empDTO) {

        Optional<EmployeeEnt> empsFindById = empRepo.findById(id);

        if (DataUtis.isNullOrEmpty(empsFindById)) {
            throw new RuntimeException("Not found employee with ID: " + id);
        }

        EmployeeEnt empEnt = mapper.toPersistenceBean(empDTO);

        return mapper.toDtoBean(empRepo.save(empEnt));
    }

    @Override
    public Optional<EmployeeEnt> findById(Long id) {
        return empRepo.findById(id);
    }

    @Override
    public void deleteEmp(Long id) {
        empRepo.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> listAll() {
        List<EmployeeEnt> listEntity = empRepo.findAll();
        List<EmployeeDTO> listEmpDto = mapper.toDtoBean(listEntity);
        return listEmpDto;
    }
}
