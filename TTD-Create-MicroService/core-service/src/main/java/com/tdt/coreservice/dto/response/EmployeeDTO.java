package com.tdt.coreservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeDTO {
    private Long id;

    private String empNo;

    private String fullName;

    private Date hireDate;
}
