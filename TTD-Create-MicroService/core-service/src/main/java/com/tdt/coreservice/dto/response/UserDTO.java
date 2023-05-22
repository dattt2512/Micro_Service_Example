package com.tdt.coreservice.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tdt.coreservice.dto.BaseDTO;
import com.tdt.coreservice.entities.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO extends BaseDTO implements Serializable {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Long roleId;
}
