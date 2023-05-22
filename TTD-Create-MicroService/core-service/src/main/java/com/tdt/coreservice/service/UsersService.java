package com.tdt.coreservice.service;

import com.tdt.coreservice.dto.response.UserDTO;

import java.util.List;

public interface UsersService {

    UserDTO findById(Long id);

    UserDTO create(UserDTO userDTO);

    UserDTO update(Long id, UserDTO userDTO);

    List<UserDTO> findAll();

    UserDTO deleteUserById(Long id);

    List<UserDTO> listAllExistUser();
}
