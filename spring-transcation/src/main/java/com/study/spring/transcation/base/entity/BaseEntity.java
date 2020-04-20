package com.study.spring.transcation.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Transient;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseEntity implements Serializable{
    @Transient
    private  String extend1;
    @Transient
    private String extend2;
    @Transient
    private String extend3;
}
