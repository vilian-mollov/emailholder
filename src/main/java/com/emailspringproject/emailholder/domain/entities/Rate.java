package com.emailspringproject.emailholder.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rates")
public class Rate extends BaseEntity {

    @Column
    private Integer rate;

    public Rate() {
    }

    public Rate(Integer rate) {
        this.rate = rate;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
