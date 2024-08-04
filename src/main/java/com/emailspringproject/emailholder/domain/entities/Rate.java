package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rates")
public class Rate extends BaseEntity {

    @Column
    private Integer rate;

}
