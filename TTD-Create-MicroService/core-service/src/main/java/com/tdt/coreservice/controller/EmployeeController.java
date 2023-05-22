package com.tdt.coreservice.controller;

import com.tdt.coreservice.dto.response.EmployeeDTO;
import com.tdt.coreservice.dto.response.UserDTO;
import com.tdt.coreservice.entities.EmployeeEnt;
import com.tdt.coreservice.service.impl.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("core/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl empService;

    @GetMapping
    public List<EmployeeDTO> listAllEmployees() {
        List<EmployeeDTO> listEmpDTO = empService.listAll();
        return listEmpDTO;
    }

    @PostMapping
    public EmployeeDTO createEmp(@RequestBody EmployeeDTO empDTO) {
        return empService.insert(empDTO);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmp(@PathVariable(value = "id") Long id, @RequestBody EmployeeDTO empDTO) {
        return empService.update(id, empDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmp(@PathVariable(value = "id") Long id) {
        Optional<EmployeeEnt> empEnt = empService.findById(id);

        if (!empEnt.isPresent()) {
            return new ResponseEntity("Not found employee with ID:" + id, HttpStatus.FORBIDDEN);
        } else {
            empService.deleteEmp(id);
            return new ResponseEntity("Employee with ID:" + id + " delete successfully", HttpStatus.OK);
        }
    }
}
