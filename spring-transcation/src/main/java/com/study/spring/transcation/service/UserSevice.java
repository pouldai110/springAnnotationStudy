package com.study.spring.transcation.service;

import com.study.spring.transcation.base.service.IBaseService;
import com.study.spring.transcation.entity.Account;

/**
 * @ClassName UserSevice
 * @Author: pouldai
 * @Date: 2020/4/16 17:54
 * @Description:
 * @Version V1.0
 */
public interface UserSevice extends IBaseService<Account,Integer> {
    boolean transforeMoneyByRequiredNew(Double money, Integer outId,Integer inId) throws Exception;
    boolean transforeMoneyByRequired(Double money, Integer outId, Integer inId) throws Exception;
    boolean transforeMoneyBySoupport(Double money, Integer outId,Integer inId) throws Exception;
    boolean transforeMoneyByNotSoupport(Double money, Integer outId,Integer inId) throws Exception;
    boolean transforeMoneyByMANDATORY(Double money, Integer outId,Integer inId) throws Exception;
    boolean transforeMoneyByNever(Double money, Integer outId,Integer inId) throws Exception;
    boolean transforeMoneyByNested(Double money, Integer outId,Integer inId) throws Exception;


}

