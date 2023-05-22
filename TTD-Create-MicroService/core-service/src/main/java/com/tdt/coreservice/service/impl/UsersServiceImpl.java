package com.tdt.coreservice.service.impl;

import com.tdt.coreservice.config.PasswordEncodeConfig;
import com.tdt.coreservice.dto.response.UserDTO;
import com.tdt.coreservice.entities.UserEntity;
import com.tdt.coreservice.mapper.BaseMapper;
import com.tdt.coreservice.repository.UserEntityPrimaryRepository;
import com.tdt.coreservice.repository.RoleEntPrimaryRepository;
import com.tdt.coreservice.repository.readOnlyDb.UserEntityReadOnlyRepository;
import com.tdt.coreservice.service.UsersService;
import com.tdt.coreservice.utils.DataUtis;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UsersServiceImpl implements UsersService {

    private static final BaseMapper<UserEntity, UserDTO> mapper = new BaseMapper<>(UserEntity.class, UserDTO.class);
    private static final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private UserEntityPrimaryRepository userPrimaryRepo;

    @Autowired
    private UserEntityReadOnlyRepository userSlaveRepo;

    @Autowired
    private RoleEntPrimaryRepository roleRepo;

    @Autowired
    private PasswordEncodeConfig passEncode;

    @Override
    public List<UserDTO> findAll() {
        //When built data replication, using line below to read data on slave DB
//        List<UserEntity> listEntity = userSlaveRepo.findAll();

        List<UserEntity> listEntity = userPrimaryRepo.findAll();
        List<UserDTO> listUserDto = mapper.toDtoBean(listEntity);
        return listUserDto;
    }

    @Override
    @Transactional
    @Cacheable(value = "userDTO")
    public UserDTO findById(Long id) {
        //When built data replication, using line below to read data on slave DB
//        Optional<UserEntity> userEnts = userSlaveRepo.findById(id);

        Optional<UserEntity> userEnts = userPrimaryRepo.findById(id);
        if (userEnts.isPresent()) {
            UserDTO dto = mapper.toDtoBean(userEnts.get());
            return dto;
        }
        return null;
    }

    @Override
    @Transactional
    @CachePut(value = "userDTO")
    public UserDTO create(UserDTO userDTO) {
        UserEntity userFindByEmail = userPrimaryRepo.findByEmail(userDTO.getEmail());

        if (!DataUtis.isNullOrEmpty(userFindByEmail)) {
            throw new RuntimeException("Email already exists, please try with another email");
        }


        String encrytedPass = passEncode.passwordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(encrytedPass);

        UserEntity userEntity = dtoMapperAndDefaultSetUp(userDTO);
        userEntity.setCreatedDate(new Date());

        return mapper.toDtoBean(userPrimaryRepo.save(userEntity));
    }

    @Override
    @Transactional
    @CachePut(value = "userDTO")
    public UserDTO update(Long id, UserDTO userDTO) {

        Optional<UserEntity> usersFindById = userPrimaryRepo.findById(id);

        if (DataUtis.isNullOrEmpty(usersFindById)) {
            throw new RuntimeException("Not found user with ID: " + id);
        }

        UserEntity userEnt = usersFindById.get();

        if (!userDTO.getPassword().equals(userEnt.getPassword())) {
            String encrytedPass = passEncode.passwordEncoder().encode(userDTO.getPassword());
            userDTO.setPassword(encrytedPass);
        }

        UserEntity userEntity = dtoMapperAndDefaultSetUp(userDTO);
        userEntity.setUpdatedDate(new Date());
        userEntity.setCreatedDate(userEnt.getCreatedDate());

        return mapper.toDtoBean(userPrimaryRepo.save(userEntity));
    }

    private UserEntity dtoMapperAndDefaultSetUp(UserDTO userDTO) {
        UserEntity userEntity;
        userEntity = mapper.toPersistenceBean(userDTO);
        userEntity.setEnabled(true);
        userEntity.setRoles(Arrays.asList(roleRepo.findById(userDTO.getRoleId()).get()));

        return userEntity;
    }

    @Override
    @Transactional
    @CacheEvict(value = "userDTO")
    public UserDTO deleteUserById(Long id) {
        UserEntity userEntToDelete = userPrimaryRepo.findById(id).get();
        if (!DataUtis.isNullOrEmpty(userEntToDelete)) {
            userEntToDelete.setDeletedDate(new Date());
            userPrimaryRepo.save(userEntToDelete);
            UserDTO dto = mapper.toDtoBean(userEntToDelete);
            return dto;
        } else {
            throw new RuntimeException("Not found user with ID: " + id);
        }
    }

    @Override
    public List<UserDTO> listAllExistUser() {
        List<UserEntity> listExistUsers = userPrimaryRepo.listAllExistUser();

        if (DataUtis.isNullOrEmpty(listExistUsers)) {
            throw new RuntimeException("No user exists in the system");
        } else {
            return mapper.toDtoBean(listExistUsers);
        }
    }
}
