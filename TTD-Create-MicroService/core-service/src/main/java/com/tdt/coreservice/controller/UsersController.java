package com.tdt.coreservice.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tdt.coreservice.dto.response.UserDTO;
import com.tdt.coreservice.entities.UserEntity;
import com.tdt.coreservice.service.UsersService;
import com.tdt.coreservice.utils.DataUtis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("core/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("")
    public List<UserDTO> listAllUsers() {
        List<UserDTO> listUserDto = usersService.findAll();
        return listUserDto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable(value="id") Long id) {
        UserDTO userDtoGetById = usersService.findById(id);
        if (DataUtis.isNullOrEmpty(userDtoGetById)) {
            return new ResponseEntity("Not found user with ID:" + id, HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(usersService.findById(id));
    }

    @PostMapping("")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return usersService.create(userDTO);
    }

    @PutMapping("/{id}")
    public UserDTO updateUser(@PathVariable(value = "id") Long id, @RequestBody UserDTO userDTO) {
        return usersService.update(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public UserDTO deleteUser(@PathVariable(value = "id") Long id) {
        return usersService.deleteUserById(id);
    }

    @GetMapping("/exist")
    public List<UserDTO> listAllExistUsers() {
        return usersService.listAllExistUser();
    }
}