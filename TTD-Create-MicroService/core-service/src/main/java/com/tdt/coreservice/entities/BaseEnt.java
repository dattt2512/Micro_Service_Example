package com.tdt.coreservice.entities;

import lombok.*;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEnt implements Serializable {

    private Date createdDate;

    private Date updatedDate;
}
